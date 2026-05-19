package Project.Attendance.Backend.DTO;

import Project.Attendance.Backend.Model.Subject;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;

import java.util.List;

public class BulkAttendance {

    private Long classId;
    private Long subjectId;
    private List<AttendanceRequest> attendanceList;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<AttendanceRequest> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceRequest> attendanceList) {
        this.attendanceList = attendanceList;
    }
}