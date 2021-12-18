package pl.turistica.service;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.turistica.dto.EnrollmentDTO;
import pl.turistica.model.Trip;
import pl.turistica.model.User;
import pl.turistica.repository.TripRepository;
import pl.turistica.repository.UserRepository;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;

@Service
public class EnrollService {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    TokenService tokenService;

    public ResponseEntity<?> enrollOrCancel(String authorizationHeader, int tripId) {
        User user = userRepository.findByEmail(tokenService.getEmailFromAuthorizationHeader(authorizationHeader));
        Trip trip = tripRepository.findTripById(tripId);
        if (user != null && trip != null) {
            if (trip.getUsers().contains(user)) {
                trip.getUsers().remove(user);
                sendEmailThread(user, trip, tripId, false);
            } else {
                if (tripRepository.countEnrolledPeopleByTripId(tripId) < trip.getPeopleLimit()) {
                    trip.getUsers().add(user);
                    sendEmailThread(user, trip, tripId, true);
                } else {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            tripRepository.save(trip);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public EnrollmentDTO getEnrollmentInfo(String authorizationHeader, int tripId) {
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


    public void sendEmailThread(User user, Trip trip, int tripId, boolean isEnrolling) {
        String subject;
        String template;
        if (isEnrolling) {
            subject = "Zapisałeś się na wyjazd!";
            template = "enroll-email-template.ftl";
        } else {
            subject = "Wypisałeś się z wyjazdu!";
            template = "unsubscribe-email-template.ftl";
        }
        try {
            emailService.sendMessage(user.getEmail(), subject, getTemplateModel(trip, user, tripId), template);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

    private HashMap<String, Object> getTemplateModel(Trip trip, User user, int tripId) {
        HashMap<String, Object> details = new HashMap<>();
        details.put("firstName", user.getName());
        details.put("lastName", user.getLastName());
        details.put("tripName", trip.getName());
        details.put("beginDate", trip.getBeginDate());
        details.put("endDate", trip.getEndDate());
        details.put("pricePerPerson", trip.getPricePerPerson());
        details.put("tripId", tripId);
        return details;
    }
}
