package attendance.backend.repository;

import attendance.backend.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByName(String name);
    Optional<Teacher> findByEmail(String email);
}
