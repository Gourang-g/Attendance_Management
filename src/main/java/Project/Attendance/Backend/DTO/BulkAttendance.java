package Project.Attendance.Backend.DTO;

import java.util.List;

public class BulkAttendance {

    private Long classId;
    private String subject;
    private List<AttendanceRequest> attendanceList;

    public Long getClassId() {
        return classId;
    }

    public String getSubject() {
        return subject;
    }

    public List<AttendanceRequest> getAttendanceList() {
        return attendanceList;
    }
}