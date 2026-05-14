package Project.Attendance.Backend.Service;

import Project.Attendance.Backend.DTO.AttendanceRequest;
import Project.Attendance.Backend.DTO.BulkAttendance;
import Project.Attendance.Backend.DTO.StudentReportDTO;
import Project.Attendance.Backend.DTO.SubjectReportDTO;
import Project.Attendance.Backend.Model.Attendance;
import Project.Attendance.Backend.Model.AttendanceStatus;
import Project.Attendance.Backend.Model.ClassEntity;
import Project.Attendance.Backend.Model.Student;
import Project.Attendance.Backend.Model.Subject;
import Project.Attendance.Backend.Repository.AttendanceRepository;
import Project.Attendance.Backend.Repository.ClassRepository;
import Project.Attendance.Backend.Repository.StudentRepository;
import Project.Attendance.Backend.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SubjectRepository subjectRepository;


    // ===============================
    // MARK ATTENDANCE
    // ===============================
    public String markAttendance(BulkAttendance request) {

        // Validate class
        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // Validate subject
        if (request.getSubjectId() == null) {
            throw new RuntimeException("Subject is required");
        }

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        LocalDate today = LocalDate.now();

        // Save attendance for each student
        for (AttendanceRequest ar : request.getAttendanceList()) {

            Student student = studentRepository.findById(ar.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            // Verify student belongs to selected class
            if (student.getClassEntity() == null ||
                    !student.getClassEntity().getId().equals(classEntity.getId())) {
                throw new RuntimeException("Student does not belong to this class");
            }

            // Prevent duplicate attendance for same student/date/subject
            boolean alreadyMarked = attendanceRepository
                    .findByStudentAndDateAndSubject(student, today, subject)
                    .isPresent();

            if (alreadyMarked) {
                continue;
            }

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setDate(today);
            attendance.setSubject(subject);
            attendance.setStatus(ar.getStatus());

            attendanceRepository.save(attendance);
        }

        return "Attendance marked successfully";
    }


    // ===============================
    // STUDENT ATTENDANCE
    // ===============================
    public List<Attendance> getStudentAttendance(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return attendanceRepository.findByStudent(student);
    }


    // ===============================
    // CLASS ATTENDANCE
    // ===============================
    public List<Attendance> getClassAttendance(Long classId) {
        return attendanceRepository.findByStudent_ClassEntity_Id(classId);
    }


    // ===============================
    // STUDENT REPORT
    // ===============================
    public StudentReportDTO getStudentReport(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Attendance> records = attendanceRepository.findByStudentId(studentId);

        long total = records.size();

        long present = records.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();

        long absent = total - present;

        double percentage = (total == 0)
                ? 0
                : (present * 100.0) / total;

        percentage = Math.round(percentage * 100.0) / 100.0;

        return new StudentReportDTO(
                student.getName(),
                student.getRollNo(),
                total,
                present,
                absent,
                percentage
        );
    }


    // ===============================
    // CLASS REPORT
    // ===============================
    public List<StudentReportDTO> getClassReport(Long classId) {

        List<Student> students = studentRepository.findByClassEntityId(classId);

        List<StudentReportDTO> report = new ArrayList<>();

        for (Student student : students) {

            List<Attendance> records = attendanceRepository.findByStudentId(student.getId());

            long total = records.size();

            long present = records.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                    .count();

            long absent = total - present;

            double percentage = (total == 0)
                    ? 0
                    : (present * 100.0) / total;

            percentage = Math.round(percentage * 100.0) / 100.0;

            report.add(new StudentReportDTO(
                    student.getName(),
                    student.getRollNo(),
                    total,
                    present,
                    absent,
                    percentage
            ));
        }

        return report;
    }


    // ===============================
    // SUBJECT-WISE REPORT
    // ===============================
    public List<SubjectReportDTO> getStudentSubjectReport(Long studentId) {

        List<Attendance> records = attendanceRepository.findByStudentId(studentId);

        // Group attendance by Subject entity
        Map<Subject, List<Attendance>> grouped =
                records.stream()
                        .collect(Collectors.groupingBy(Attendance::getSubject));

        List<SubjectReportDTO> result = new ArrayList<>();

        for (Map.Entry<Subject, List<Attendance>> entry : grouped.entrySet()) {

            Subject subject = entry.getKey();
            List<Attendance> subjectRecords = entry.getValue();

            long total = subjectRecords.size();

            long present = subjectRecords.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                    .count();

            double percentage = (total == 0)
                    ? 0
                    : (present * 100.0) / total;

            percentage = Math.round(percentage * 100.0) / 100.0;

            // Assumes SubjectReportDTO accepts String subject name
            result.add(new SubjectReportDTO(
                    subject,
                    total,
                    present,
                    percentage
            ));
        }

        return result;
    }


    // ===============================
    // FILTER BY CLASS AND SUBJECT
    // ===============================
    public List<Attendance> getAttendanceByClassAndSubject(Long classId, Long subject) {

        Subject subject1 = subjectRepository.findById(subject)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        return attendanceRepository
                .findByStudent_ClassEntity_IdAndSubject(classId, subject1);
    }


    // ===============================
    // FILTER BY CLASS, SUBJECT AND DATE
    // ===============================
    public List<Attendance> getAttendanceByClassSubjectAndDate(
            Long classId,
            Long subjectId,
            LocalDate date
    ) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        return attendanceRepository
                .findByStudent_ClassEntity_IdAndSubjectAndDate(
                        classId,
                        subject,
                        date
                );
    }
}
