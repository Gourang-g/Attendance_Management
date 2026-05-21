package Project.Attendance.Backend.DTO;

public class LowAttendanceDTO {

    private String name;
    private String rollNo;
    private String className;
    private double percentage;

    public LowAttendanceDTO() {
    }

    public LowAttendanceDTO(String name,
                            String rollNo,
                            String className,
                            double percentage) {
        this.name = name;
        this.rollNo = rollNo;
        this.className = className;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getClassName() {
        return className;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}