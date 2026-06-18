package attendance.backend.repository;

import attendance.geolocation.CampusLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampusLocationRepository extends JpaRepository<CampusLocation, Long> {
    List<CampusLocation> findByIsActive(Boolean isActive);
    List<CampusLocation> findByIsActiveTrue();


}
