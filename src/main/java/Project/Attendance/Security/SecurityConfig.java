// file: src/main/java/Project/Attendance/Security/SecurityConfig.java
package Project.Attendance.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ========== PUBLIC ENDPOINTS (No Authentication) ==========
                        .requestMatchers("/api/auth/student/register").permitAll()
                        .requestMatchers("/api/auth/teacher/login").permitAll()
                        .requestMatchers("/api/auth/student/login").permitAll()
                        .requestMatchers("/api/admin/login").permitAll()
                        .requestMatchers("/api/admin/register").permitAll()

                        // ========== ADMIN ONLY ENDPOINTS ==========
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Class Management
                        .requestMatchers("/api/classes/create").hasRole("ADMIN")
                        .requestMatchers("/api/classes").permitAll()  // All authenticated can view classes
                        .requestMatchers("/api/classes/**").hasRole("ADMIN")  // Only admin can modify

                        // Department Management (Admin only)
                        .requestMatchers("/api/departments").hasAnyRole("ADMIN", "TEACHER")  // GET allowed for ADMIN/TEACHER
                        .requestMatchers("/api/departments/**").hasRole("ADMIN")  // POST/DELETE allowed for ADMIN

                        // Subject Management
                        .requestMatchers("/api/subjects").permitAll()  // All authenticated can view subjects
                        .requestMatchers("/api/subjects/**").hasRole("ADMIN")  // Only admin can modify

                        // Dashboard - Role specific
                        .requestMatchers("/api/dashboard/admin").hasRole("ADMIN")
                        .requestMatchers("/api/dashboard/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/api/dashboard/student/**").hasRole("STUDENT")

                        // ========== TEACHER ENDPOINTS ==========
                        .requestMatchers("/api/attendance/mark").hasRole("TEACHER")
                        .requestMatchers("/api/attendance/class/**").hasRole("TEACHER")
                        .requestMatchers("/api/attendance/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                        .requestMatchers("/api/teacher/**").hasRole("TEACHER")

                        // ========== STUDENT ENDPOINTS ==========
                        .requestMatchers("/api/student/**").hasRole("STUDENT")

                        // ========== SHARED ENDPOINTS (Multiple Roles) ==========
                        // Attendance viewing (Teachers, Students, Admins can view)
                        .requestMatchers("/api/attendance/low-attendance").hasAnyRole("ADMIN", "TEACHER")

                        // Students by class (Teachers and Admins can view)
                        .requestMatchers("/api/classes/*/students").hasAnyRole("ADMIN", "TEACHER")

                        // Teachers list (Publicly available)
                        .requestMatchers("/api/teachers").permitAll()

                        // Students list (Admin and Teachers can view)
                        .requestMatchers("/api/students").hasAnyRole("ADMIN", "TEACHER")

                        // Teacher registration by admin
                        .requestMatchers("/api/auth/teacher/register").hasRole("ADMIN")

                        // ========== DEFAULT: All other requests require authentication ==========
                        .anyRequest().authenticated()
                );

        // Add JWT filter before the default authentication filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors(cors -> cors.configurationSource(request -> {
            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
            corsConfig.setAllowedOriginPatterns(java.util.List.of("*"));
            corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfig.setAllowedHeaders(java.util.List.of("*"));
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        }));

        return http.build();
    }
}