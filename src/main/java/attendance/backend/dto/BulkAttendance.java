package attendance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkAttendance {

    private Long classId;
    private Long subjectId;
    private List<AttendanceRequest> attendanceList;
}