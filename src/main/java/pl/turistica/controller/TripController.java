package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.turistica.dto.TripDTO;
import pl.turistica.dto.TripGeneralInfoDTO;
import pl.turistica.model.Trip;
import pl.turistica.service.TripService;

import java.util.List;

@RestController
    @CrossOrigin(origins = {"http://localhost:4200", "https://turistica.herokuapp.com"})
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping("/trip-details/{id}")
    public Trip getTripDetails(@PathVariable int id) {
        return tripService.getTripDetails(id);
    }

    @PostMapping("/create-trip")
    public ResponseEntity<?> createNewTrip(@RequestBody TripDTO trip) {
        return tripService.createNewTrip(trip);
    }

    @PutMapping("/edit-trip")
    public ResponseEntity<?> editTrip(@RequestBody Trip trip) {
        return tripService.editTrip(trip);
    }

    @GetMapping("/trips/all")
    public List<TripGeneralInfoDTO> getAllTripsFiltered(
            @RequestParam(required = false, defaultValue = "1970-01-01", value = "beginDate") String beginDate,
            @RequestParam(required = false, defaultValue = "2099-12-31", value = "endDate") String endDate) {
        return tripService.getAllTripsFiltered(beginDate, endDate);
    }

    @GetMapping("/trips/archive")
    public List<TripGeneralInfoDTO> getArchiveTrips() {
        return tripService.getArchiveTrips();
    }

}
