package pl.turistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.turistica.model.TripType;

@Repository
public interface TripTypeRepository extends JpaRepository<TripType, Integer> {

    TripType findTripTypeById(int id);
}
