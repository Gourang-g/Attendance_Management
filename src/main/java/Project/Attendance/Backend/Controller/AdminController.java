package Project.Attendance.Backend.Controller;

import Project.Attendance.Backend.DTO.AdminRegisterDTO;
import Project.Attendance.Backend.DTO.LoginResponseDTO;
import Project.Attendance.Backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password) {
        try {
            LoginResponseDTO admin = adminService.adminLogin(username, password);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdminRegisterDTO register) {
        try{
            String message = adminService.registerAdmin(register);
            return ResponseEntity.ok(message);
        }
        catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}
