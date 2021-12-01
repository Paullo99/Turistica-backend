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
    TokenService tokenService;

    @Autowired
    EmailService emailService;

    @PostMapping("/trips/enroll")
    public ResponseEntity<?> enrollOrCancel(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestParam(value = "tripId") int tripId) {

        User user = userRepository.findByEmail(tokenService.getEmailFromAuthorizationHeader(authorizationHeader));

        if (user != null) {
            Trip trip = tripRepository.findTripById(tripId);
            if (trip != null) {
                HashMap<String, Object> details = new HashMap<>();
                details.put("firstName", user.getName());
                details.put("lastName", user.getLastName());
                details.put("tripName", trip.getName());
                details.put("beginDate", trip.getBeginDate());
                details.put("endDate", trip.getEndDate());
                details.put("pricePerPerson", trip.getPricePerPerson());
                details.put("tripId", tripId);
                if (trip.getUsers().contains(user)) {
                    trip.getUsers().remove(user);

                    new Thread(() -> {
                        try {
                            emailService.sendMessage(user.getEmail(),
                                    "Wypisałeś się z wyjazdu!", details,
                                    "unsubscribe-email-template.ftl");
                        } catch (MessagingException | IOException | TemplateException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else{
                    if (tripRepository.countEnrolledPeopleByTripId(tripId) < trip.getPeopleLimit()){
                        trip.getUsers().add(user);
                        new Thread(() -> {
                            try {
                                emailService.sendMessage(user.getEmail(),
                                        "Zapisałeś się na wyjazd!", details,
                                        "enroll-email-template.ftl");
                            } catch (MessagingException | IOException | TemplateException e) {
                                e.printStackTrace();
                            }
                        }).start();

                    }
                    else{
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }
                }
                tripRepository.save(trip);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/trips/enrollment-info")
    public EnrollmentDTO getEnrollmentInfo(@RequestHeader(name="Authorization", required = false) String authorizationHeader,
                                         @RequestParam(value = "tripId") int tripId) {
        boolean isEnrolled = false;
        int enrolledPeople = 0;
        Trip trip = tripRepository.findTripById(tripId);
        if(trip!=null){
            if(authorizationHeader!=null && !authorizationHeader.equals("")){
                User user = userRepository.findByEmail(tokenService.getEmailFromAuthorizationHeader(authorizationHeader));
                if(user!=null){
                    if(trip.getUsers().contains(user))
                        isEnrolled = true;
                }
            }

            enrolledPeople = tripRepository.countEnrolledPeopleByTripId(tripId);
        }

        return new EnrollmentDTO(isEnrolled, enrolledPeople);

    }
}
