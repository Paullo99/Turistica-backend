package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.turistica.dto.TripGeneralInfoDTO;
import pl.turistica.model.Trip;
import pl.turistica.repository.TripRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @GetMapping("/trips/all")
    public List<TripGeneralInfoDTO> getAllTrips(){
        return  tripRepository.findTripsAfterToday();
    }

    @GetMapping("/trips/archive")
    public List<TripGeneralInfoDTO> getArchiveTrips(){
        return  tripRepository.findTripsBeforeToday();
    }

    @GetMapping("/trip-details/{id}")
    public Trip getTripDetails(@PathVariable int id){
        return tripRepository.findTripById(id);
    }
}
