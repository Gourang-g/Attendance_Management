package Project.Attendance.Backend.Service;

import Project.Attendance.Backend.DTO.LoginResponseDTO;
import Project.Attendance.Backend.DTO.StudentRegister;
import Project.Attendance.Backend.DTO.TeacherRegister;
import Project.Attendance.Backend.Model.ClassEntity;
import Project.Attendance.Backend.Model.Student;
import Project.Attendance.Backend.Model.Teacher;
import Project.Attendance.Backend.Repository.ClassRepository;
import Project.Attendance.Backend.Repository.StudentRepository;
import Project.Attendance.Backend.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    //to register student
    public String registerStudent(StudentRegister register) {
        // student check
        if (studentRepository.findByRollNo(register.getRollNo()).isPresent()) {
            return "Student with Roll no already exists!";
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
    public LoginResponseDTO studentLogin(String name, String password) {
        Optional<Student> student = studentRepository.findByName(name);
        if (student.isPresent() && password.equals(student.get().getPassword())) {

            return new LoginResponseDTO(
                    student.get().getId(),
                    student.get().getName(),
                    "student",
                    "Login Success"
            );
        }
        return null;
    }

    //teacher register
    public String registerTeacher(TeacherRegister register){
        if(teacherRepository.findByName(register.getName()).isPresent()){
            return "Teacher already exists!";
        }

        Teacher teacher = new Teacher();
        teacher.setName(register.getName());
        teacher.setEmail(register.getEmail());
        teacher.setSubject(register.getSubject());
        teacher.setDepartment(register.getDepartment());
        teacher.setPassword(register.getPassword());

        teacherRepository.save(teacher);

        return "Teacher registered successfully!";
    }

    //teacher login
    public LoginResponseDTO teacherLogin(String name, String password) {
        Optional<Teacher> teacher = teacherRepository.findByName(name);
        if (teacher.isPresent() && password.equals(teacher.get().getPassword())) {

            return new LoginResponseDTO(
                    teacher.get().getId(),
                    teacher.get().getName(),
                    "teacher",
                    "Login Success"
            );
        }
        return null;
    }
}
