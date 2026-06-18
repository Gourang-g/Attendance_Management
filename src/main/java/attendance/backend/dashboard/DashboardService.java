package attendance.backend.dashboard;

import attendance.backend.dto.AdminDashboardDTO;
import attendance.backend.dto.TeacherDashboardDTO;
import attendance.backend.repository.*;
import attendance.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import attendance.backend.repository.*;

@Service
public class DashboardService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    public AdminDashboardDTO getAdminDashboard() {
        return new AdminDashboardDTO(
                departmentRepository.count(),
                subjectRepository.count(),
                classRepository.count(),
                teacherRepository.count(),
                studentRepository.count(),
                attendanceRepository.count(),
                0
        );
    }

    // Add these methods inside DashboardService.java

    public TeacherDashboardDTO getTeacherDashboard(Long teacherId) {
        // Total classes assigned to this teacher
        long assignedClasses = classRepository.countByTeacherId(teacherId);

        // Total subjects assigned to this teacher
        // If you have a Subject.teacher relationship and repository method:
        // long assignedSubjects = subjectRepository.countByTeacherId(teacherId);
        // Otherwise keep 0 for now.
        long assignedSubjects = 0;

        // Total students in teacher's classes
        long totalStudents = studentRepository.countByClassEntityTeacherId(teacherId);

        // Attendance marked today by this teacher
        // Requires attendanceRepository.countByTeacherIdAndDate(...)
        // If Attendance does not store teacher, keep 0 for now.
        long attendanceMarkedToday = 0;

        // Students below 75% in this teacher's classes
        // Can be implemented later.
        long lowAttendanceStudents = 0;

        return new TeacherDashboardDTO(
                assignedClasses,
                assignedSubjects,
                totalStudents,
                attendanceMarkedToday,
                lowAttendanceStudents
        );
    }
}

    /*public StudentDashboardDTO getStudentDashboard(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Attendance> records =
                attendanceRepository.findByStudentId(studentId);

        long totalClasses = records.size();

        long present = records.stream()
                .filter(record -> record.getStatus() == AttendanceStatus.PRESENT)
                .count();

        long absent = totalClasses - present;

        double percentage = totalClasses > 0
                ? (present * 100.0) / totalClasses
                : 0.0;

        // Count subjects where attendance is below 75%
        Map<String, List<Attendance>> subjectGroups = records.stream()
                .collect(Collectors.groupingBy(Attendance::getSubject));

        long subjectsBelow75 = subjectGroups.values().stream()
                .filter(subjectRecords -> {
                    long total = subjectRecords.size();
                    long subjectPresent = subjectRecords.stream()
                            .filter(record -> record.getStatus() == AttendanceStatus.PRESENT)
                            .count();

                    double subjectPercentage = total > 0
                            ? (subjectPresent * 100.0) / total
                            : 0.0;

                    return subjectPercentage < 75.0;
                })
                .count();

        return new StudentDashboardDTO(
                student.getName(),
                student.getRollNo(),
                totalClasses,
                present,
                absent,
                Math.round(percentage * 100.0) / 100.0,
                subjectsBelow75
        );
    }*/

