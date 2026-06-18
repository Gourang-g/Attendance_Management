package attendance.passwordreset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${app.frontend.url:http://localhost:8080}")
    private String frontendurl;

    public boolean sendPasswordResetEmail(String toEmail, String resetToken, String userName) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            if (mailFrom != null && !mailFrom.isBlank()) {
                message.setFrom(mailFrom);
            }
            message.setTo(toEmail);
            message.setSubject("Password Reset Request");

            String resetLink = frontendurl + "/reset-password.html?token=" + resetToken; //change to frontendlink

            String emailBody = "Hello " + userName + ",\n\n" +
                    "You requested a password reset. Click the link below to reset your password:\n\n" +
                    resetLink + "\n\n" +
                    "This link will expire in 1 hour.\n\n" +
                    "If you didn't request this, please ignore this email.\n\n" +
                    "Best regards,\nAttendance System";

            message.setText(emailBody);
            mailSender.send(message);
            logger.info("Email Sent to {}", toEmail);
            return true;
        }
        catch(Exception e){
            logger.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
