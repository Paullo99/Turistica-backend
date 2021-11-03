package pl.turistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.turistica.dto.TripGeneralInfoDTO;
import pl.turistica.model.Trip;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findAll();

    @Query("SELECT new pl.turistica.dto.TripGeneralInfoDTO(t.id, t.name, t.tripType, t.beginDate, t.endDate, t.pricePerPerson) " +
            "FROM Trip t " +
            "WHERE t.beginDate >= curdate() " +
            "ORDER BY t.beginDate")
    List<TripGeneralInfoDTO> findTripsAfterToday();

    @Query("SELECT new pl.turistica.dto.TripGeneralInfoDTO(t.id, t.name, t.tripType, t.beginDate, t.endDate, t.pricePerPerson) " +
            "FROM Trip t " +
            "WHERE t.beginDate < curdate() " +
            "ORDER BY t.beginDate")
    List<TripGeneralInfoDTO> findTripsBeforeToday();

    Trip findTripById(int id);
}
