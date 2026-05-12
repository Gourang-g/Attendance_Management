package Project.Attendance.Backend.Repository;

import Project.Attendance.Backend.Model.Attendance;
import Project.Attendance.Backend.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(Student student);
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findAttendanceByDate(LocalDate date);
    List<Attendance> findByStudentAndDate(Student student, LocalDate date);
    List<Attendance> findByStudent_ClassEntity_Id(Long classId);
    Optional<Attendance> findByStudentAndDateAndSubject(
            Student student, LocalDate date, String subject
    );
    boolean existsByStudentAndDate(Student student, LocalDate date);

    List<Attendance> findByStudent_ClassEntity_IdAndSubject(
            Long classId,
            String subject
    );
    List<Attendance> findByStudent_ClassEntity_IdAndSubjectAndDate(
            Long classId,
            String subject,
            LocalDate date
    );
}
