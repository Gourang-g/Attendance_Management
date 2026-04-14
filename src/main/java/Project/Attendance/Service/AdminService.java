package Project.Attendance.Service;

import Project.Attendance.Model.Admin;
import Project.Attendance.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin adminLogin(@RequestParam String username, @RequestParam String password) {
        Admin admin = adminRepository.findByusername(username).orElseThrow(() -> new RuntimeException("Admin not found!"));
        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        return admin;
    }
}
