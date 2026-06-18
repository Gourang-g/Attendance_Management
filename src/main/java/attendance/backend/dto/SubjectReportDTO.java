package attendance.backend.dto;

import attendance.backend.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectReportDTO {
    private Subject subject;
    private long total;
    private long present;
    private double percentage;
}
