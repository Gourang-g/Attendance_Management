
package attendance.backend.dto;

import attendance.backend.model.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequest {

    private Long studentId;
    private AttendanceStatus status;
    private String subject;
}
