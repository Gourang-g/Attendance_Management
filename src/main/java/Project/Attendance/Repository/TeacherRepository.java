package Project.Attendance.Repository;

import Project.Attendance.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByName(String name);
    Optional<Teacher> findByNameAndPassword(String name, String password);
}
