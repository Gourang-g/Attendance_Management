package Project.Attendance.Backend.DTO;

import Project.Attendance.Backend.Model.Subject;

public class SubjectReportDTO {
    private Subject subject;
    private long total;
    private long present;
    private double percentage;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPresent() {
        return present;
    }

    public void setPresent(long present) {
        this.present = present;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public SubjectReportDTO(Subject subject, long total, long present, double percentage) {
        this.subject = subject;
        this.total = total;
        this.present = present;
        this.percentage = percentage;
    }
}
