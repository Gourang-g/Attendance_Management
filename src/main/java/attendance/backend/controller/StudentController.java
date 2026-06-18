package attendance.backend.controller;

import attendance.backend.model.Student;
import attendance.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("/class/{classId}")
    public List<Student> getStudentsByClass(@PathVariable Long classId){
        return studentRepository.findByClassEntityId(classId);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam String keyword){

        List<Student> byName =
                studentRepository.findByNameContainingIgnoreCase(keyword);

        List<Student> byRoll =
                studentRepository.findByRollNoContainingIgnoreCase(keyword);

        if(!byName.isEmpty()){
            return byName;
        }

        return byRoll;
    }
}
