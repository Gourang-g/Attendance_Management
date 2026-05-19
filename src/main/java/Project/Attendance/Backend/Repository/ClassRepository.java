package Project.Attendance.Backend.Repository;

import Project.Attendance.Backend.Model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    List<ClassEntity> findByTeacher_Id(Long teacherId);
    long countByTeacherId(Long teacherId);
}
