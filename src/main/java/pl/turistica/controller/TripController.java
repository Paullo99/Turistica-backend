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
import pl.turistica.service.TokenService;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripTypeRepository tripTypeRepository;

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
                trip.getBeginDate(), trip.getEndDate(), trip.getPricePerPerson(), trip.getPeopleLimit(), trip.getDescription(),
                trip.getMap());

        if (tripRepository.save(tripToInsert) != null)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);

    }
}
