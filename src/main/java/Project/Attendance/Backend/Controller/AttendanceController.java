package Project.Attendance.Backend.Controller;

import Project.Attendance.Backend.DTO.BulkAttendance;
import Project.Attendance.Backend.DTO.StudentReportDTO;
import Project.Attendance.Backend.DTO.SubjectReportDTO;
import Project.Attendance.Backend.Model.Attendance;
import Project.Attendance.Backend.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

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


}
