package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.turistica.dto.EnrollmentDTO;
import pl.turistica.service.EnrollService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://turistica.herokuapp.com"})
public class EnrollController {

    @Autowired
    EnrollService enrollService;

    @PostMapping("/trips/enroll")
    public ResponseEntity<?> enrollOrCancel(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestParam(value = "tripId") int tripId) {
        return enrollService.enrollOrCancel(authorizationHeader, tripId);
    }

    @GetMapping("/trips/enrollment-info")
    public EnrollmentDTO getEnrollmentInfo(@RequestHeader(name = "Authorization", required = false) String authorizationHeader,
                                           @RequestParam(value = "tripId") int tripId) {
        return enrollService.getEnrollmentInfo(authorizationHeader, tripId);
    }
}
