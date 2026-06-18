package attendance.geolocation;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "campus_locations")
public class CampusLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String campusName;

    @Column(nullable = false)
    private Double latitude;      // Center point

    @Column(nullable = false)
    private Double longitude;     // Center point

    @Column(nullable = false)
    private Double radiusInMeters; // Geofence radius

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isActive = true;

    public CampusLocation(Long id, String campusName, Double latitude, Double longitude, Double radiusInMeters, LocalDateTime createdAt, Boolean isActive) {
        this.id = id;
        this.campusName = campusName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radiusInMeters = radiusInMeters;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    protected CampusLocation() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
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

    public Double getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(Double radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
