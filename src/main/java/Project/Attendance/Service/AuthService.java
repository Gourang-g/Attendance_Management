package Project.Attendance.Service;

import Project.Attendance.DTO.StudentRegister;
import Project.Attendance.Model.*;
import Project.Attendance.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;

    //to register student
    public String registerStudent(StudentRegister register) {

        // if student already there
        if (studentRepository.findByRollNo(register.getRollNo()).isPresent()) {
            return "Student with Rollno already exists!";
        }

        Optional<ClassEntity> classOptional = classRepository.findById(register.getClassId());
        if (classOptional.isEmpty()) {
            return "Class not found!";
        }
        // create student
        Student student = new Student();
        student.setRollNo(register.getRollNo());
        student.setName(register.getName());
        student.setBranch(register.getBranch());
        student.setSemester(register.getSemester());
        student.setYear(register.getYear());
        student.setPassword(register.getPassword());
        student.setClassEntity(classOptional.get());
        studentRepository.save(student);
        return "Student registered successfully!";
    }

    //login(student)
    public String studentLogin(String name, String password) {
        Optional<Student> student = studentRepository.findByName(name);
        if(student.isPresent() && password.equals(student.get().getPassword())) {
            return "Student login successfully!";}
        return "Student login failed!";
    }
}
