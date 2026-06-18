package attendance.backend.repository;

import attendance.backend.model.Student;
import attendance.geolocation.StudentLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentLocationRepository extends JpaRepository<StudentLocation, Long> {
    List<StudentLocation> findByStudent(Student student);
}
