package pl.turistica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.turistica.dto.TripDTO;
import pl.turistica.dto.TripGeneralInfoDTO;
import pl.turistica.model.Trip;
import pl.turistica.repository.TripRepository;
import pl.turistica.repository.TripTypeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripTypeRepository tripTypeRepository;

    public List<TripGeneralInfoDTO> getArchiveTrips() {
        return tripRepository.findTripsBeforeToday();
    }

    public ResponseEntity<?> createNewTrip(TripDTO trip) {
        Trip tripToInsert = new Trip(trip.getName(), tripTypeRepository.findTripTypeById(trip.getTripType()),
                trip.getBeginDate(), trip.getEndDate(), trip.getPricePerPerson(), trip.getPeopleLimit(), trip.getDescription(),
                trip.getMap());

        try {
            tripRepository.save(tripToInsert);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> editTrip(Trip trip) {
        Trip oldTrip = tripRepository.findTripById(trip.getId());
        trip.setUsers(oldTrip.getUsers());
        try {
            tripRepository.save(trip);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<TripGeneralInfoDTO> getAllTripsFiltered(String beginDate, String endDate) {

        if (beginDate.equals("1970-01-01") && endDate.equals("2099-12-31"))
            return tripRepository.findTripsAfterToday();
        else {
            if (LocalDate.parse(beginDate).isBefore(LocalDate.now()))
                beginDate = LocalDate.now().toString();
            return tripRepository.findTripsBetweenTwoDates(LocalDate.parse(beginDate), LocalDate.parse(endDate));
        }
    }

    public Trip getTripDetails(int id) {
        return tripRepository.findTripById(id);
    }
}
