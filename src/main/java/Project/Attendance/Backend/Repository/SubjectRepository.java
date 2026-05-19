package Project.Attendance.Backend.Repository;

import Project.Attendance.Backend.Model.Department;
import Project.Attendance.Backend.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByDepartmentId(Long departmentId);
    List<Subject> findBySemester(String semester);
}
