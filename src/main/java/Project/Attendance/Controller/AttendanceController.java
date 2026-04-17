package Project.Attendance.Controller;

import Project.Attendance.DTO.BulkAttendance;
import Project.Attendance.Model.Attendance;
import Project.Attendance.Repository.AttendanceRepository;
import Project.Attendance.Service.AttendanceService;
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

}
