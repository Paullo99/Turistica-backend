package pl.turistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.turistica.model.Trip;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findAll();

    @Query(value="SELECT * FROM Trip t WHERE t.begin_date < curdate() ORDER BY t.begin_date DESC", nativeQuery = true)
    List<Trip> findAllTripsBeforeNow();

    @Query(value="SELECT * FROM Trip t WHERE t.begin_date >= curdate() ORDER BY t.begin_date", nativeQuery = true)
    List<Trip> findAllTripsAfterNow();

    List<Trip> findTripById(int id);
}
