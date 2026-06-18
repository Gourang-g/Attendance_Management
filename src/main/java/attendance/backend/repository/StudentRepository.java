package attendance.backend.repository;

import attendance.backend.model.ClassEntity;
import attendance.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNo(String rollNo);
    Optional<Student> findByName(String name);
    Optional<Student> findById(Long studentId);
    Optional<Student> findByEmail(String email);

    List<Student> findByClassEntity(ClassEntity classEntity);
    List<Student> findByClassEntityId(Long classEntityId);
    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByRollNoContainingIgnoreCase(String rollNo);

    long countByClassEntityTeacherId(Long teacherId);
}
