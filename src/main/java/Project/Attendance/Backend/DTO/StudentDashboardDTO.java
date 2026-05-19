// StudentDashboardDTO.java
package Project.Attendance.Backend.DTO;

public class StudentDashboardDTO {

    private String name;
    private String rollNo;
    private long totalClasses;
    private long present;
    private long absent;
    private double percentage;
    private long subjectsBelow75;

    public StudentDashboardDTO() {
    }

    public StudentDashboardDTO(String name,
                               String rollNo,
                               long totalClasses,
                               long present,
                               long absent,
                               double percentage,
                               long subjectsBelow75) {
        this.name = name;
        this.rollNo = rollNo;
        this.totalClasses = totalClasses;
        this.present = present;
        this.absent = absent;
        this.percentage = percentage;
        this.subjectsBelow75 = subjectsBelow75;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
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

    public long getSubjectsBelow75() {
        return subjectsBelow75;
    }

    public void setSubjectsBelow75(long subjectsBelow75) {
        this.subjectsBelow75 = subjectsBelow75;
    }
}