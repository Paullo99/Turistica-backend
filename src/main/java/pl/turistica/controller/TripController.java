package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.turistica.dto.TripGeneralInfoDTO;
import pl.turistica.model.Trip;
import pl.turistica.repository.TripRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @GetMapping("/trips/all")
    public List<TripGeneralInfoDTO> getAllTripsFiltered(
            @RequestParam(required = false, defaultValue = "1970-01-01", value="beginDate") String beginDate,
            @RequestParam(required = false, defaultValue = "2099-12-31", value="endDate") String endDate){

        if(beginDate.equals("1970-01-01") && endDate.equals("2099-12-31"))
            return  tripRepository.findTripsAfterToday();
        else{
            if(LocalDate.parse(beginDate).isBefore(LocalDate.now()))
                beginDate=LocalDate.now().toString();
            return  tripRepository.findTripsBetweenTwoDates(LocalDate.parse(beginDate), LocalDate.parse(endDate));
        }

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
