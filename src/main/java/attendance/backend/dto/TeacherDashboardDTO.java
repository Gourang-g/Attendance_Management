// TeacherDashboardDTO.java
package attendance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDashboardDTO {

    private long assignedClasses;
    private long assignedSubjects;
    private long totalStudents;
    private long attendanceMarkedToday;
    private long lowAttendanceStudents;
}