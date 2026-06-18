package attendance.backend.controller;

import attendance.backend.dto.LoginResponseDTO;
import attendance.backend.dto.StudentRegister;
import attendance.backend.dto.TeacherRegister;
import attendance.backend.service.AuthService;
import attendance.passwordreset.ForgetPasswordDTO;
import attendance.passwordreset.PasswordResetService;
import attendance.passwordreset.ResetPasswordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final PasswordResetService passwordResetService;

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

    @PostMapping("/forget-password")
    public String forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        return passwordResetService.initiatePasswordReset(
                forgetPasswordDTO.getEmail(),
                forgetPasswordDTO.getUserType()
        );
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return passwordResetService.resetPassword(
                resetPasswordDTO.getToken(),
                resetPasswordDTO.getNewPassword(),
                resetPasswordDTO.getConfirmPassword()
        );
    }
}