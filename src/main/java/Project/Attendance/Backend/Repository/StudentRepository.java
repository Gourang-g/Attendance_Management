package Project.Attendance.Backend.Repository;

import Project.Attendance.Backend.Model.ClassEntity;
import Project.Attendance.Backend.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNo(String rollNo);
    Optional<Student> findByName(String name);
    Optional<Student> findByNameAndPassword(String name, String password);
    Optional<Student> findById(Long studentId);

    List<Student> findByClassEntity(ClassEntity classEntity);
    List<Student> findByClassEntityId(Long classEntityId);
    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByRollNoContainingIgnoreCase(String rollNo);

    long countByClassEntityTeacherId(Long teacherId);
}
