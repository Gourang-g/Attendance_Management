package attendance.backend.repository;

import attendance.backend.model.Attendance;
import attendance.backend.model.Student;
import attendance.backend.model.Subject;
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
