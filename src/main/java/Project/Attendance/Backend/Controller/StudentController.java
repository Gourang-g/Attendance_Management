package Project.Attendance.Backend.Controller;

import Project.Attendance.Backend.Model.Student;
import Project.Attendance.Backend.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/class/{classId}")
    public List<Student> getStudentsByClass(@PathVariable Long classId){
        return studentRepository.findByClassEntityId(classId);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
}
