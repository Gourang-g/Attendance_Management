package Project.Attendance.Repository;

import Project.Attendance.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByusername(String username);
    //Optional<Admin> findByusernameAndpassword(String username, String password);
}
