package Project.Attendance.Backend.DTO;

import java.util.List;

public class StudentReportDTO {
    private String name;
    private String rollno;
    private long totalClasses;
    private long present;
    private long absent;
    private double percentage;
    private List<AttendanceRecordDTO> records;

    public StudentReportDTO(String name, String rollno, long totalClasses, long present, long absent, double percentage, List<AttendanceRecordDTO> records) {
        this.name = name;
        this.rollno = rollno;
        this.totalClasses = totalClasses;
        this.present = present;
        this.absent = absent;
        this.percentage = percentage;
        this.records = records;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public long getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(long totalClasses) {
        this.totalClasses = totalClasses;
    }

    public long getPresent() {
        return present;
    }

    public void setPresent(long present) {
        this.present = present;
    }

    public long getAbsent() {
        return absent;
    }

    public void setAbsent(long absent) {
        this.absent = absent;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public List<AttendanceRecordDTO> getRecords() {
        return records;
    }

    public void setRecords(List<AttendanceRecordDTO> records) {
        this.records = records;
    }
}
