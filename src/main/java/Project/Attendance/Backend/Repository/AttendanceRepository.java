package Project.Attendance.Backend.Repository;

import Project.Attendance.Backend.Model.Attendance;
import Project.Attendance.Backend.Model.Student;
import Project.Attendance.Backend.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(Student student);
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByStudent_ClassEntity_Id(Long classId);
    Optional<Attendance> findByStudentAndDateAndSubject(
            Student student, LocalDate date, Subject subject
    );

    List<Attendance> findByStudent_ClassEntity_IdAndSubject(
            Long classId,
            Subject subject
    );
    List<Attendance> findByStudent_ClassEntity_IdAndSubjectAndDate(
            Long classId,
            Subject subject,
            LocalDate date
    );
}
