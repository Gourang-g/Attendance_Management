package attendance.backend.controller;

import attendance.backend.dto.AdminRegisterDTO;
import attendance.backend.dto.LoginResponseDTO;
import attendance.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

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
