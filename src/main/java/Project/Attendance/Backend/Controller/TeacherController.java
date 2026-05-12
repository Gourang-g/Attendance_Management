package Project.Attendance.Backend.Controller;

import Project.Attendance.Backend.Model.Teacher;
import Project.Attendance.Backend.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping
    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }
}