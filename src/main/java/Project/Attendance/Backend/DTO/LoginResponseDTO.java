package Project.Attendance.Backend.DTO;

public class LoginResponseDTO {
    private Long id;
    private String name;
    private String role;
    private String message;

    public LoginResponseDTO(Long id, String name, String role, String message) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }
}

