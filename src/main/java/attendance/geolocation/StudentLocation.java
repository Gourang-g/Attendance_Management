package attendance.geolocation;

import attendance.backend.model.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_location_history")
public class StudentLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private LocalDateTime checkInTime;

    @Column(nullable = false)
    private Boolean isInsideCampus;

    @Column(nullable = false)
    private Double distanceFromCampus; // Distance in meters

    @ManyToOne
    @JoinColumn(name = "campus_id")
    private CampusLocation campusLocation;

    protected StudentLocation() {
    }

    public StudentLocation(Long id, Student student, Double latitude, Double longitude, LocalDateTime checkInTime, Boolean isInsideCampus, Double distanceFromCampus, CampusLocation campusLocation) {
        this.id = id;
        this.student = student;
        this.latitude = latitude;
        this.longitude = longitude;
        this.checkInTime = checkInTime;
        this.isInsideCampus = isInsideCampus;
        this.distanceFromCampus = distanceFromCampus;
        this.campusLocation = campusLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

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

    public CampusLocation getCampusLocation() {
        return campusLocation;
    }

    public void setCampusLocation(CampusLocation campusLocation) {
        this.campusLocation = campusLocation;
    }
}
