package Project.Attendance.Backend.DTO;

public class LoginResponseDTO {
    private Long id;
    private String name;
    private String role;
    private String message;
    private String token;

    public LoginResponseDTO(Long id, String name, String role, String message, String token) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.message = message;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

