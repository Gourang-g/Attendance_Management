package attendance.backend.controller;

import attendance.backend.dto.*;
import attendance.backend.model.Attendance;
import attendance.backend.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/mark")
    public String markAttendance(@RequestBody BulkAttendance bulkAttendance) {
        return attendanceService.markAttendance(bulkAttendance);
    }
    @GetMapping("/student/{studentId}")
    public List<Attendance> getstudentAttendance(@PathVariable("studentId") Long studentId) {
        return attendanceService.getStudentAttendance(studentId);
    }
    @GetMapping("/class/{classId}")
    public List<Attendance> getclassAttendance(@PathVariable Long classId) {
        return attendanceService.getClassAttendance(classId);
    }
    //studentrepot
    // Full report (records + percentage)
    @GetMapping("/student/{id}/report")
    public StudentReportDTO getFullReport(@PathVariable Long id) {
        return attendanceService.getStudentReport(id);
    }
    // Class report
    @GetMapping("/class/{classId}/report")
    public List<StudentReportDTO> getClassReport(@PathVariable Long classId) {
        return attendanceService.getClassReport(classId);
    }
    // Subject-wise student report
    @GetMapping("/student/{id}/subject-report")
    public List<SubjectReportDTO> getSubjectReport(@PathVariable Long id) {
        return attendanceService.getStudentSubjectReport(id);
    }

    @GetMapping("/class/{classId}/subject")
    public List<Attendance> getAttendanceBySubject(
            @PathVariable Long classId,
            @RequestParam Long subjectId
    ) {
        return attendanceService
                .getAttendanceByClassAndSubject(classId, subjectId);
    }

    @GetMapping("/class/{classId}/subject/date")
    public List<Attendance> getAttendanceByDate(
            @PathVariable Long classId,
            @RequestParam Long subjectId,
            @RequestParam LocalDate date
    ) {
        return attendanceService
                .getAttendanceByClassSubjectAndDate(
                        classId,
                        subjectId,
                        date
                );
    }

    @GetMapping("/low-attendance")
    public List<LowAttendanceDTO> getLowAttendanceStudents(
            @RequestParam(defaultValue = "75") double threshold) {
        return attendanceService.getLowAttendanceStudents(threshold);
    }

    @PutMapping("/{attendanceId}")
    public Attendance updateAttendance(
            @PathVariable Long attendanceId,
            @RequestBody UpdateAttendanceDTO dto) {
        return attendanceService.updateAttendance(attendanceId, dto);
    }

}
