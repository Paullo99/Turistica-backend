package pl.turistica;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.turistica.dto.TripDTO;
import pl.turistica.model.TripType;
import pl.turistica.repository.TripRepository;
import pl.turistica.repository.TripTypeRepository;
import pl.turistica.service.TripService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripTypeRepository tripTypeRepository;

    @InjectMocks
    private TripService tripService;

    @Test
    void shouldCreateNewTrip(){
        //given
        TripDTO tripDTO = new TripDTO();
        TripType tripType = new TripType();

        //when
        when(tripTypeRepository.findTripTypeById(tripDTO.getTripType())).thenReturn(tripType);
        ResponseEntity<?> responseEntity = tripService.createNewTrip(tripDTO);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
