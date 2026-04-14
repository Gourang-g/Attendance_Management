package Project.Attendance.Service;

import Project.Attendance.Repository.ClassRepository;
import Project.Attendance.Repository.StudentRepository;
import Project.Attendance.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassService {
    @Autowired
    ClassRepository classRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
}
