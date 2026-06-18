
package attendance.passwordreset;

import attendance.backend.model.Admin;
import attendance.backend.model.Student;
import attendance.backend.model.Teacher;
import attendance.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import attendance.backend.repository.AdminRepository;
import attendance.backend.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import attendance.backend.repository.StudentRepository;
import attendance.backend.repository.TeacherRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String initiatePasswordReset(String email, String userType) {
        if (email == null || email.isEmpty()) {
            return "If an account with that email exists, a password reset link will be sent.";
        }

        if (userType == null ) {
            userType = "";
        }

        userType = userType.toUpperCase();
        boolean userExists = false;
        String userName = "";

        // Check if user exists
        if ("STUDENT".equals(userType)) {
            Optional<Student> student = studentRepository.findByEmail(email);
            if (student.isPresent()) {
                userExists = true;
                userName = student.get().getName();
            }
        } else if ("TEACHER".equals(userType)) {
            Optional<Teacher> teacher = teacherRepository.findByEmail(email);
            if (teacher.isPresent()) {
                userExists = true;
                userName = teacher.get().getName();
            }
        } else if ("ADMIN".equals(userType)) {
            Optional<Admin> admin = adminRepository.findByEmail(email);
            if (admin.isPresent()) {
                userExists = true;
                userName = admin.get().getName();
            }
        } else {
            return"If an account with that email exists, a password reset link will be sent.";
        }

        if (!userExists) {
            return  "If an account with that email exists, a password reset link will be sent.";
        }

        // Generate reset token (1 hour expiry)
        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);

        // Save token to database
        PasswordResetToken resetToken = new PasswordResetToken(token, email, userType, expiresAt);
        tokenRepository.save(resetToken);

        // Send email
        try {
            boolean emailsent = emailService.sendPasswordResetEmail(email, token, userName);
            if (!emailsent) {
                return "Failed to send password reset email. Please try again later.";
            }
        } catch (Exception e) {
            return "An error occurred while sending the password reset email. Please try again later.";
        }
        return "Password reset link sent to your email";
    }

    public String resetPassword(String token, String newPassword, String confirmPassword) {
        if (token == null || token.isEmpty()) {
            return "Token is required";
        }

        if (newPassword == null || newPassword.isEmpty()) {
            return "New password is required";
        }

        if (!newPassword.equals(confirmPassword)) {
            return "Passwords do not match";
        }

        if (newPassword.length() < 6) {
            return "Password must be at least 6 characters long";
        }

        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByToken(token);

        if (resetTokenOpt.isEmpty()) {
            return "Invalid token";
        }

        PasswordResetToken resetToken = resetTokenOpt.get();

        if (resetToken.isExpired()) {
            return "Token has expired";
        }

        if (resetToken.isUsed()) {
            return "Token has already been used";
        }

        String userType = resetToken.getUserType();
        String email = resetToken.getUserEmail();
        String encodedPassword = passwordEncoder.encode(newPassword);

        boolean updated = false;

        // Update password based on user type
        if ("STUDENT".equals(userType)) {
            Optional<Student> student = studentRepository.findByEmail(email);
            if (student.isPresent()) {
                student.get().setPassword(encodedPassword);
                studentRepository.save(student.get());
                updated = true;
            }
        } else if ("TEACHER".equals(userType)) {
            Optional<Teacher> teacher = teacherRepository.findByEmail(email);
            if (teacher.isPresent()) {
                teacher.get().setPassword(encodedPassword);
                teacherRepository.save(teacher.get());
                updated = true;
            }
        } else if ("ADMIN".equals(userType)) {
            Optional<Admin> admin = adminRepository.findByEmail(email);
            if (admin.isPresent()) {
                admin.get().setPassword(encodedPassword);
                adminRepository.save(admin.get());
                updated = true;
            }
        }
        if (!updated) {
            return "User not found. Password reset failed.";
        }

        // Mark token as used
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        return "Password reset successfully!";
    }
}