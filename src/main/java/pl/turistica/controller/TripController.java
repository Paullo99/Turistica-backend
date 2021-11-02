package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.turistica.model.Trip;
import pl.turistica.repository.TripRepository;

import java.util.List;

@RestController
@RequestMapping(path="/trips")
@CrossOrigin(origins = "http://localhost:4200")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @GetMapping("/all")
    public List<Trip> getAllTrips(){
        return  tripRepository.findAllTripsAfterNow();
    }

    @GetMapping("/archive")
    public List<Trip> getArchiveTrips(){
        return  tripRepository.findAllTripsBeforeNow();
    }
}
