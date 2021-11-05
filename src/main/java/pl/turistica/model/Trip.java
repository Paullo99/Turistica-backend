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
    private String tripType;
    private LocalDate beginDate;
    private LocalDate endDate;
    private int pricePerPerson;
    private int enrolledPeople;
    private int limit;
    private String description;
    private byte[] exampleImage;

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

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(int pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public int getEnrolledPeople() {
        return enrolledPeople;
    }

    public void setEnrolledPeople(int enrolledPeople) {
        this.enrolledPeople = enrolledPeople;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getExampleImage() {
        return exampleImage;
    }

    public void setExampleImage(byte[] exampleImage) {
        this.exampleImage = exampleImage;
    }
}