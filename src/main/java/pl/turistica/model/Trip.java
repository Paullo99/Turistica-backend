package pl.turistica.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String place;
    private LocalDate tripBeginDate;
    private LocalDate tripEndDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getTripBeginDate() {
        return tripBeginDate;
    }

    public void setTripBeginDate(LocalDate tripBeginDate) {
        this.tripBeginDate = tripBeginDate;
    }

    public LocalDate getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(LocalDate tripEndDate) {
        this.tripEndDate = tripEndDate;
    }
}
