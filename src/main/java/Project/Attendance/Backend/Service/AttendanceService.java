package Project.Attendance.Backend.Service;

import Project.Attendance.Backend.DTO.AttendanceRequest;
import Project.Attendance.Backend.DTO.BulkAttendance;
import Project.Attendance.Backend.Model.Attendance;
import Project.Attendance.Backend.Model.ClassEntity;
import Project.Attendance.Backend.Model.Student;
import Project.Attendance.Backend.Repository.AttendanceRepository;
import Project.Attendance.Backend.Repository.ClassRepository;
import Project.Attendance.Backend.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;

    public String markAttendance(BulkAttendance request) {
        ClassEntity classEntity = classRepository.findById(request.getClassId()).orElseThrow(() -> new RuntimeException("Class not found"));
        LocalDate today = LocalDate.now();

        String subject = request.getSubject().toUpperCase();

        for (AttendanceRequest ar : request.getAttendanceList()) {
            Student student = studentRepository.findById(ar.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));

            if (student.getClassEntity() == null ||
                    !student.getClassEntity().getId().equals(classEntity.getId())) {
                throw new RuntimeException("Student does not belong to this class");
            }

            boolean alreadyMarked = attendanceRepository
                    .findByStudentAndDateAndSubject(student, today, subject)
                    .isPresent();
            if (alreadyMarked) {
                continue; // skip duplicate
            }

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setDate(today);
            attendance.setSubject(subject); // ✅ use uppercase subject
            attendance.setStatus(ar.getStatus());

            attendanceRepository.save(attendance);
        }

        return "Attendance marked successfully";
    }


    public List<Attendance> getStudentAttendance(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return attendanceRepository.findByStudent(student);
    }


    public List<Attendance> getClassAttendance(Long classId) {

        return attendanceRepository.findByStudent_ClassEntity_Id(classId);
    }
}