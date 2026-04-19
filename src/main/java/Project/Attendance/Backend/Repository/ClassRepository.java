package Project.Attendance.Backend.Repository;

import Project.Attendance.Backend.Model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

}
