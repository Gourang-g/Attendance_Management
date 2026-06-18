package attendance.backend.controller;

import attendance.backend.model.ClassEntity;
import attendance.backend.model.Student;
import attendance.backend.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    @PostMapping("/create")
    public ClassEntity createClass(@RequestBody ClassEntity classEntity) {
        return classService.createClass(classEntity);
    }

    @GetMapping
    public List<ClassEntity> getAllClasses() {
        return classService.getAllClasses();
    }

    @PutMapping("/{classId}/assign-teacher/{teacherId}")
    public ClassEntity assignTeacher(@PathVariable Long classId,
                                     @PathVariable Long teacherId){
        return classService.assignTeacher(classId, teacherId);
    }

    @PutMapping("/{classId}/add-student/{studentId}")
    public Student assignStudentToClass(@PathVariable Long classId,
                                        @PathVariable Long studentId){
        return classService.assignStudentToClass(studentId, classId);
    }

    @GetMapping("/{classId}/students")
    public List<Student> getStudentsByClass(@PathVariable Long classId){
        return classService.getStudentsByClass(classId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<ClassEntity> getClassesByTeacher(@PathVariable Long teacherId){
        return classService.getClassesByTeacher(teacherId);
    }
}