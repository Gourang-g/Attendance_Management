package Project.Attendance.Backend.Controller;

import Project.Attendance.Backend.DTO.LoginResponseDTO;
import Project.Attendance.Backend.DTO.StudentRegister;
import Project.Attendance.Backend.DTO.TeacherRegister;
import Project.Attendance.Backend.Service.AuthService;
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
    public LoginResponseDTO studentLogin(@RequestParam String name, @RequestParam String password) {
        return authService.studentLogin(name, password);
    }

    @PostMapping("/teacher/register")
    public String registerTeacher(@RequestBody TeacherRegister register) {
        return authService.registerTeacher(register);
    }

    @PostMapping("/teacher/login")
    public LoginResponseDTO teacherLogin(@RequestParam String name, @RequestParam String password) {
        return authService.teacherLogin(name, password);
    }
}