// C:\Users\VICTUS\Desktop\Springboot\Attendance\src\main\java\Project\Attendance\Geolocation\GeolocationController.java
package attendance.backend.controller;

import attendance.geolocation.CheckinDTO;
import attendance.geolocation.GeolocationService;
import attendance.geolocation.ResponseDTO;
import attendance.geolocation.StudentLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/geolocation")
public class GeolocationController {

    private final GeolocationService geolocationService;

    /**
     * Student checkin with GPS coordinates
     */
    @PostMapping("/checkin")
    public ResponseDTO studentCheckin(@RequestBody CheckinDTO checkinDTO) {
        return geolocationService.checkInStudent(checkinDTO);
    }

    /**
     * Get student's location history
     */
    @GetMapping("/history/{studentId}")
    public List<StudentLocation> getLocationHistory(@PathVariable Long studentId) {
        return geolocationService.getStudentLocationHistory(studentId);
    }

    /**
     * Calculate distance between two coordinates
     */
    @GetMapping("/distance")
    public Double calculateDistance(
            @RequestParam Double lat1,
            @RequestParam Double lon1,
            @RequestParam Double lat2,
            @RequestParam Double lon2) {
        return geolocationService.calculateDistance(lat1, lon1, lat2, lon2);
    }
}