package attendance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportDTO {

    private String name;
    private String rollNo;
    private long totalClasses;
    private long present;
    private long absent;
    private double percentage;
}