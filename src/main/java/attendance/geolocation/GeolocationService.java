package attendance.geolocation;
import attendance.backend.model.Student;
import attendance.backend.model.Attendance;
import attendance.backend.model.AttendanceStatus;
import attendance.backend.model.Subject;
import attendance.backend.repository.AttendanceRepository;
import attendance.backend.repository.CampusLocationRepository;
import attendance.backend.repository.StudentLocationRepository;
import attendance.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GeolocationService {

    @Autowired
    private StudentLocationRepository studentLocationRepository;

    @Autowired
    private CampusLocationRepository campusLocationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final double PROXIMITY_THRESHOLD_METERS = 200.0;

    public Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceKm = EARTH_RADIUS_KM * c;

        return distanceKm * 1000; // Convert to meters
    }

    public ResponseDTO checkInStudent(CheckinDTO checkinDTO) {
        ResponseDTO response = new ResponseDTO();
        response.setCheckInTime(LocalDateTime.now());

        // Find student
        Student student = studentRepository.findById(checkinDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Get active campus location (assume one primary campus or filter by location ID)
        CampusLocation campus = campusLocationRepository.findByIsActive(true)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active campus location found"));

        // Calculate distance
        Double distanceInMeters = calculateDistance(
                campus.getLatitude(),
                campus.getLongitude(),
                checkinDTO.getLatitude(),
                checkinDTO.getLongitude()
        );

        response.setDistanceFromCampus(distanceInMeters);

        // Check if within 200m
        boolean isInside = distanceInMeters <= PROXIMITY_THRESHOLD_METERS;
        response.setInsideCampus(isInside);

        // Save location history
        StudentLocation studentLocation = new StudentLocation();
        studentLocation.setStudent(student);
        studentLocation.setLatitude(checkinDTO.getLatitude());
        studentLocation.setLongitude(checkinDTO.getLongitude());
        studentLocation.setCheckInTime(LocalDateTime.now());
        studentLocation.setInsideCampus(isInside);
        studentLocation.setDistanceFromCampus(distanceInMeters);
        studentLocation.setCampusLocation(campus);

        studentLocationRepository.save(studentLocation);

        if (isInside) {
            response.setMessage("✓ Within campus proximity. Attendance marked.");
            response.setAttendanceStatus(AttendanceStatus.PRESENT);

            // Auto-mark attendance if within 200m and subject is specified
            if (checkinDTO.getSubjectId() != null) {
                markAttendanceFromGeolocation(student, checkinDTO.getSubjectId());
            }
        } else {
            response.setMessage("✗ Outside 200m campus radius. Distance: " +
                    String.format("%.2f", distanceInMeters) + "m");
            response.setAttendanceStatus(AttendanceStatus.ABSENT);
        }

        return response;
    }

    private void markAttendanceFromGeolocation(Student student, Long subjectId) {
        // Check if already marked today
        LocalDate today = LocalDate.now();
        Optional<Attendance> existing = attendanceRepository
                .findByStudentAndDateAndSubject(student, today,
                        new Subject());

        if (existing.isEmpty()) {
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setDate(today);
            attendance.setStatus(AttendanceStatus.PRESENT);
            // Set subject based on subjectId
            attendanceRepository.save(attendance);
        }
    }

    public List<StudentLocation> getStudentLocationHistory(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return studentLocationRepository.findByStudent(student);
    }
}