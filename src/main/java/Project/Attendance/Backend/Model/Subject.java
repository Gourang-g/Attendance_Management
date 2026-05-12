package Project.Attendance.Backend.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = true)
    private String code;
    @Column(nullable = false)
    private String name;
    private String semester;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Subject(){}
    public Subject(String code, String name, String semester, Department department) {
        this.code = code;
        this.name = name;
        this.semester = semester;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
