package Project.Attendance.Backend.Controller;

import Project.Attendance.Backend.Model.Subject;
import Project.Attendance.Backend.Service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // Add a new subject
    @PostMapping
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectService.addSubject(subject);
    }

    // Get all subjects
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    // Get subjects by department
    @GetMapping("/department/{departmentId}")
    public List<Subject> getSubjectsByDepartment(@PathVariable Long departmentId) {
        return subjectService.getSubjectsByDepartment(departmentId);
    }

    // Get subjects by semester
    @GetMapping("/semester/{semester}")
    public List<Subject> getSubjectsBySemester(@PathVariable String semester) {
        return subjectService.getSubjectsBySemester(semester);
    }

    // Delete a subject
    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "Subject deleted successfully";
    }
}