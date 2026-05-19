package Project.Attendance.Backend.Service;

import Project.Attendance.Backend.Model.Department;
import Project.Attendance.Backend.Model.Subject;
import Project.Attendance.Backend.Repository.DepartmentRepository;
import Project.Attendance.Backend.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectService(DepartmentRepository departmentRepository, SubjectRepository subjectRepository) {
        this.departmentRepository = departmentRepository;
        this.subjectRepository = subjectRepository;
    }

    public Subject addSubject(Subject subject) {
        if(subject.getDepartment() == null || subject.getDepartment().getId() == null){
            throw new RuntimeException("Department is Required");
        }
        Department department = departmentRepository.findById(subject.getDepartment().getId()).orElseThrow(() -> new RuntimeException("Department not found"));

        subject.setDepartment(department);
        return subjectRepository.save(subject);
    }

    public List<Subject> getallSubjects(){
        return subjectRepository.findAll();
    }
    public List<Subject> getSubjectsByDepartment(Long id){
        return subjectRepository.findByDepartmentId(id);
    }
    public List<Subject> getSubjectsBySemester(String semester){
        return subjectRepository.findBySemester(semester);
    }
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }


}
