package Project.Attendance.Controller;

import Project.Attendance.Model.ClassEntity;
import Project.Attendance.Repository.ClassRepository;
import Project.Attendance.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

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
}
