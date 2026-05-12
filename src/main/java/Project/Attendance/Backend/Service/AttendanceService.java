package Project.Attendance.Backend.Service;

import Project.Attendance.Backend.DTO.*;
import Project.Attendance.Backend.Model.Attendance;
import Project.Attendance.Backend.Model.AttendanceStatus;
import Project.Attendance.Backend.Model.ClassEntity;
import Project.Attendance.Backend.Model.Student;
import Project.Attendance.Backend.Repository.AttendanceRepository;
import Project.Attendance.Backend.Repository.ClassRepository;
import Project.Attendance.Backend.Repository.StudentRepository;
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

    //studentreport
    public StudentReportDTO getStudentReport(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Attendance> records = attendanceRepository.findByStudentId(studentId);

        long total = records.size();

        long present = records.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();

        long absent = total - present;

        double percentage = (total == 0) ? 0 : (present * 100.0) / total;
        percentage = Math.round(percentage * 100.0) / 100.0;

        // Convert to DTO
        List<AttendanceRecordDTO> recordDTOs = records.stream()
                .map(a -> new AttendanceRecordDTO(
                        a.getDate(),
                        a.getSubject(),
                        a.getStatus().name()
                ))
                .toList();

        return new StudentReportDTO(
                student.getName(),
                student.getRollNo(),
                total,
                present,
                absent,
                percentage
        );
    }

    //classreport
    public List<StudentReportDTO> getClassReport(Long classId) {

        List<Student> students = studentRepository.findByClassEntityId(classId);

        List<StudentReportDTO> report = new ArrayList<>();

        for (Student student : students) {

            List<Attendance> records =
                    attendanceRepository.findByStudentId(student.getId());

            long total = records.size();

            long present = records.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                    .count();

            long absent = total - present;

            double percentage = (total == 0) ? 0 :
                    (present * 100.0) / total;

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

    //subjectwise
    public List<SubjectReportDTO> getStudentSubjectReport(Long studentId) {

        List<Attendance> records =
                attendanceRepository.findByStudentId(studentId);

        Map<String, List<Attendance>> grouped =
                records.stream().collect(Collectors.groupingBy(Attendance::getSubject));

        List<SubjectReportDTO> result = new ArrayList<>();

        for (String subject : grouped.keySet()) {

            List<Attendance> subjectRecords = grouped.get(subject);

            long total = subjectRecords.size();

            long present = subjectRecords.stream()
                    .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                    .count();

            double percentage = (total == 0) ? 0 :
                    (present * 100.0) / total;

            result.add(new SubjectReportDTO(subject, total, present, percentage));
        }

        return result;
    }
    public List<Attendance> getAttendanceByClassAndSubject(
            Long classId,
            String subject
    ){
        return attendanceRepository
                .findByStudent_ClassEntity_IdAndSubject(classId, subject);
    }
    public List<Attendance> getAttendanceByClassSubjectAndDate(
            Long classId,
            String subject,
            LocalDate date
    ){
        return attendanceRepository
                .findByStudent_ClassEntity_IdAndSubjectAndDate(
                        classId,
                        subject,
                        date
                );
    }
}