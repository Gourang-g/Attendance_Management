package Project.Attendance.Backend.Controller;

import Project.Attendance.Backend.Model.Admin;
import Project.Attendance.Backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Admin login(@RequestParam String username, @RequestParam String password){
        return adminService.adminLogin(username, password);
    }

}
