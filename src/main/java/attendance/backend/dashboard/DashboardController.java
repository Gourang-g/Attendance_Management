package attendance.backend.dashboard;

import attendance.backend.dto.AdminDashboardDTO;
import attendance.backend.dto.TeacherDashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/admin")
    public AdminDashboardDTO getAdminDashboard() {
        return dashboardService.getAdminDashboard();
    }

    @GetMapping("/teacher/{teacherId}")
    public TeacherDashboardDTO getTeacherDashboard(@PathVariable Long teacherId) {
        return dashboardService.getTeacherDashboard(teacherId);
    }
}
