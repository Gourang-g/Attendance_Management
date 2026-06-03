package Project.Attendance.Geolocation;

import Project.Attendance.Backend.Model.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private Boolean isInsideCampus;
    private Double distanceFromCampus;
    private String message;
    private AttendanceStatus attendanceStatus;
    private LocalDateTime checkInTime;

    public Boolean getInsideCampus() {
        return isInsideCampus;
    }

    public void setInsideCampus(Boolean insideCampus) {
        isInsideCampus = insideCampus;
    }

    public Double getDistanceFromCampus() {
        return distanceFromCampus;
    }

    public void setDistanceFromCampus(Double distanceFromCampus) {
        this.distanceFromCampus = distanceFromCampus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }
}
