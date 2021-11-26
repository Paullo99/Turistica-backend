package pl.turistica.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.turistica.dto.TripDTO;
import pl.turistica.dto.TripGeneralInfoDTO;
import pl.turistica.model.Trip;
import pl.turistica.model.User;
import pl.turistica.repository.TripRepository;
import pl.turistica.repository.TripTypeRepository;
import pl.turistica.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripTypeRepository tripTypeRepository;

    @PostMapping("/trips/enroll")
    public ResponseEntity<?> enrollOrCancel(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestParam(value = "tripId") int tripId) {
        String token = authorizationHeader.split(" ")[1];
        String email = new String(Base64.decodeBase64(token)).split(":")[0];

        User user = userRepository.findByEmail(email);

        if (user != null) {
            Trip trip = tripRepository.findTripById(tripId);
            if (trip != null) {
                if (trip.getUsers().contains(user)) {
                    trip.getUsers().remove(user);
                } else {
                    trip.getUsers().add(user);
                }
                tripRepository.save(trip);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/trips/all")
    public List<TripGeneralInfoDTO> getAllTripsFiltered(
            @RequestParam(required = false, defaultValue = "1970-01-01", value = "beginDate") String beginDate,
            @RequestParam(required = false, defaultValue = "2099-12-31", value = "endDate") String endDate) {

        if (beginDate.equals("1970-01-01") && endDate.equals("2099-12-31"))
            return tripRepository.findTripsAfterToday();
        else {
            if (LocalDate.parse(beginDate).isBefore(LocalDate.now()))
                beginDate = LocalDate.now().toString();
            return tripRepository.findTripsBetweenTwoDates(LocalDate.parse(beginDate), LocalDate.parse(endDate));
        }
    }

    @GetMapping("/trips/archive")
    public List<TripGeneralInfoDTO> getArchiveTrips() {
        return tripRepository.findTripsBeforeToday();
    }

    @GetMapping("/trip-details/{id}")
    public Trip getTripDetails(@PathVariable int id) {
        return tripRepository.findTripById(id);
    }

    @PostMapping("/create-trip")
    public ResponseEntity<?> createNewTrip(@RequestBody TripDTO trip) {
        Trip tripToInsert = new Trip(trip.getName(), tripTypeRepository.findTripTypeById(trip.getTripType()),
                trip.getBeginDate(), trip.getEndDate(), trip.getPricePerPerson(), trip.getLimit(), trip.getDescription(),
                trip.getMap());

        if (tripRepository.save(tripToInsert) != null)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);

    }
}
