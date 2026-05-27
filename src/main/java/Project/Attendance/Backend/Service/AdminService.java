package Project.Attendance.Backend.Service;

import Project.Attendance.Backend.DTO.AdminRegisterDTO;
import Project.Attendance.Backend.DTO.LoginResponseDTO;
import Project.Attendance.Backend.Model.Admin;
import Project.Attendance.Backend.Repository.AdminRepository;
import Project.Attendance.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AdminService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AdminRepository adminRepository;

    public LoginResponseDTO adminLogin(@RequestParam String username, @RequestParam String password) {
        Admin admin = adminRepository.findByusername(username).orElseThrow(() -> new RuntimeException("Admin not found!"));
        if (!bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }String token = jwtUtil.generateToken(admin.getUsername(), "ADMIN");
        return new LoginResponseDTO(admin.getId(), admin.getName(), "admin", "Login Success", token);
    }

    public String registerAdmin(AdminRegisterDTO register) {
        if (adminRepository.findByusername(register.getUsername()).isPresent()) {
            return "Admin already exists!";
        }

        Admin admin =  new  Admin();
        admin.setUsername(register.getUsername());
        admin.setName(register.getName());
        admin.setPassword(bCryptPasswordEncoder.encode(register.getPassword()));
        admin.setRole("ADMIN");

        adminRepository.save(admin);
        return "Admin registered successfully!";
    }
}
