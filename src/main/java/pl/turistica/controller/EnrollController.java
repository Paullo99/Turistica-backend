package pl.turistica.controller;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.turistica.dto.EnrollmentDTO;
import pl.turistica.model.Trip;
import pl.turistica.model.User;
import pl.turistica.repository.TripRepository;
import pl.turistica.repository.UserRepository;
import pl.turistica.service.EmailService;
import pl.turistica.service.EnrollService;
import pl.turistica.service.TokenService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    EnrollService enrollService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/trips/enroll")
    public ResponseEntity<?> enrollOrCancel(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestParam(value = "tripId") int tripId) {

        User user = userRepository.findByEmail(tokenService.getEmailFromAuthorizationHeader(authorizationHeader));
        Trip trip = tripRepository.findTripById(tripId);
        if (user != null && trip != null) {
            if (trip.getUsers().contains(user)) {
                trip.getUsers().remove(user);
                enrollService.sendEmailThread(user, trip, tripId, false);
            } else {
                if (tripRepository.countEnrolledPeopleByTripId(tripId) < trip.getPeopleLimit()) {
                    trip.getUsers().add(user);
                    enrollService.sendEmailThread(user, trip, tripId, true);
                } else {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            tripRepository.save(trip);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/trips/enrollment-info")
    public EnrollmentDTO getEnrollmentInfo(@RequestHeader(name = "Authorization", required = false) String authorizationHeader,
                                           @RequestParam(value = "tripId") int tripId) {
        boolean isEnrolled = false;
        int enrolledPeople = 0;
        Trip trip = tripRepository.findTripById(tripId);
        if (trip != null) {
            if (authorizationHeader != null && !authorizationHeader.equals("")) {
                User user = userRepository.findByEmail(tokenService.getEmailFromAuthorizationHeader(authorizationHeader));
                if (user != null) {
                    if (trip.getUsers().contains(user))
                        isEnrolled = true;
                }
            }

            enrolledPeople = tripRepository.countEnrolledPeopleByTripId(tripId);
        }

        return new EnrollmentDTO(isEnrolled, enrolledPeople);

    }
}
