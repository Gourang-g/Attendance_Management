package attendance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LowAttendanceDTO {

    private String name;
    private String rollNo;
    private String className;
    private double percentage;
}