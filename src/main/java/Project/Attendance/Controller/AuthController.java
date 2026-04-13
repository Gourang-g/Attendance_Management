package Project.Attendance.Controller;

import Project.Attendance.DTO.StudentRegister;
import Project.Attendance.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/student/register")
    public String registerStudent(@RequestBody StudentRegister register) {
        return authService.registerStudent(register);
    }

    @PostMapping("/student/login")
    public String studentLogin(@RequestParam String name, @RequestParam String password) {
        return authService.studentLogin(name, password);
    }

}