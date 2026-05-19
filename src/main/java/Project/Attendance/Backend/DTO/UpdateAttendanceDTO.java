package Project.Attendance.Backend.DTO;

public class UpdateAttendanceDTO {

    private String status;

    public UpdateAttendanceDTO() {
    }

    public UpdateAttendanceDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}