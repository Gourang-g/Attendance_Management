package attendance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegister {
    private String rollNo;
    private String name;
    private String branch;
    private String semester;
    private String year;
    private String password;
    private String email;
    private Long classId;
}