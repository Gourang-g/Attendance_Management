package attendance.security;

import attendance.backend.model.Admin;
import attendance.backend.model.Student;
import attendance.backend.model.Teacher;
import attendance.backend.repository.AdminRepository;
import attendance.backend.repository.StudentRepository;
import attendance.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByusername(username).orElse(null);
        if (admin != null) {
            return User.builder().username(admin.getUsername()).password(admin.getPassword()).authorities(new SimpleGrantedAuthority("ROLE_ADMIN")).build();
        }

        Teacher teacher = teacherRepository.findByName(username).orElse(null);
        if (teacher != null) {
            return User.builder()
                    .username(teacher.getName())
                    .password(teacher.getPassword())
                    .authorities(new SimpleGrantedAuthority("ROLE_TEACHER"))
                    .build();
        }

        Student student = studentRepository.findByName(username).orElse(null);
        if (student != null) {
            return User.builder()
                    .username(student.getName())
                    .password(student.getPassword())
                    .authorities(new SimpleGrantedAuthority("ROLE_STUDENT"))
                    .build();
        }
        throw new UsernameNotFoundException("user not found");
    }

}
