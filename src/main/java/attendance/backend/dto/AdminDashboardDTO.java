
package attendance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {

    private long totalDepartments;
    private long totalSubjects;
    private long totalClasses;
    private long totalTeachers;
    private long totalStudents;
    private long totalAttendanceRecords;
    private long lowAttendanceStudents;
}