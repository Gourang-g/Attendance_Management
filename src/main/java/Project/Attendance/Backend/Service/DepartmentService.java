package Project.Attendance.Backend.Service;

import Project.Attendance.Backend.Model.Department;
import Project.Attendance.Backend.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department addDepartment(Department department) {
        if
        (departmentRepository.findByName(department.getName()).isPresent()) {
            throw new RuntimeException(department.getName() + " already exists");
        }
        return departmentRepository.save(department);
    }


}
