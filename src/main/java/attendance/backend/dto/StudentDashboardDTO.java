// StudentDashboardDTO.java
package attendance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDashboardDTO {

    private String name;
    private String rollNo;
    private long totalClasses;
    private long present;
    private long absent;
    private double percentage;
    private long subjectsBelow75;
}