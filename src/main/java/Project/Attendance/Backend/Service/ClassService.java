package Project.Attendance.Backend.Service;

import Project.Attendance.Backend.Model.ClassEntity;
import Project.Attendance.Backend.Model.Student;
import Project.Attendance.Backend.Model.Teacher;
import Project.Attendance.Backend.Repository.ClassRepository;
import Project.Attendance.Backend.Repository.StudentRepository;
import Project.Attendance.Backend.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    ClassRepository classRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    //create class
    public ClassEntity createClass(ClassEntity classEntity) {
        return classRepository.save(classEntity);
    }
    public List<ClassEntity> getAllClasses(){
        return classRepository.findAll();
    }
    //assign teacher to a class
    public ClassEntity assignTeacher(Long classId, Long teacherId){
    ClassEntity classEntity = classRepository.findById(classId).orElseThrow();
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        classEntity.setTeacher(teacher);
        return classRepository.save(classEntity);
    }
    //assisign student to a class
    public Student assignStudenttoClass(Long studentId, Long classId)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        student.setClassEntity(classEntity);
        return studentRepository.save(student);
    }
    //view student
    public List<Student> getStudentsByClass(Long classId){
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        return studentRepository.findByClassEntity(classEntity);
    }
}
