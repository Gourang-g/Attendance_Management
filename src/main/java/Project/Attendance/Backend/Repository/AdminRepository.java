package Project.Attendance.Backend.Repository;

import Project.Attendance.Backend.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByusername(String username);

}
