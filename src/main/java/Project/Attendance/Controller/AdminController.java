package Project.Attendance.Controller;

import Project.Attendance.DTO.StudentRegister;
import Project.Attendance.Model.Admin;
import Project.Attendance.Repository.AdminRepository;
import Project.Attendance.Service.AdminService;
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
