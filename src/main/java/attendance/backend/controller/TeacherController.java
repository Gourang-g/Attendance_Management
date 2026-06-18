package attendance.backend.controller;

import attendance.backend.model.Teacher;
import attendance.backend.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @GetMapping
    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }
}