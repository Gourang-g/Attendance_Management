package Project.Attendance.Backend.Controller;

import Project.Attendance.Backend.Model.ClassEntity;
import Project.Attendance.Backend.Model.Student;
import Project.Attendance.Backend.Repository.ClassRepository;
import Project.Attendance.Backend.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin
public class
ClassController {

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
    @PutMapping("/{classId}/add-student/{studentId}")
    public Student assignStudenttoClass(@PathVariable Long classId,
                                        @PathVariable Long studentId){
        return classService.assignStudenttoClass(studentId,classId);
    }
    @GetMapping("/{classId}/students")
    public List<Student> getStudentsByClass(@PathVariable Long classId){
        //return classService.getStudentsByClass(classId);
        return classService.getStudentsByClass(classId);
    }
    @GetMapping("/teacher/{teacherId}")
    public List<ClassEntity> getClassesByTeacher(@PathVariable Long teacherId){
        return classService.getClassesByTeacher(teacherId);
    }
}
