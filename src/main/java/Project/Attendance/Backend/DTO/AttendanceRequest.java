
package Project.Attendance.Backend.DTO;

import Project.Attendance.Backend.Model.AttendanceStatus;

public class AttendanceRequest {

    private Long studentId;
    private AttendanceStatus status;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}