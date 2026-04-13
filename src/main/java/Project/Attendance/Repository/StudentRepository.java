package Project.Attendance.Repository;

import Project.Attendance.Model.ClassEntity;
import Project.Attendance.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByRollNo(String rollNo);
    Optional<Student> findByName(String name);
    Optional<Student> findByNameAndPassword(String name, String password);

    List<Student> findByClassEntity(ClassEntity classEntity);
}
