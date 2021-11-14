package pl.turistica.dto;

import pl.turistica.model.TripType;

import java.time.LocalDate;

public class TripGeneralInfoDTO {
    private int id;
    private String name;
    private String tripType;
    private LocalDate beginDate;
    private LocalDate endDate;
    private int pricePerPerson;

    public TripGeneralInfoDTO() {}

    public TripGeneralInfoDTO(int id, String name, TripType tripType, LocalDate beginDate, LocalDate endDate, int pricePerPerson) {
        this.id = id;
        this.name = name;
        this.tripType = tripType.getName();
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.pricePerPerson = pricePerPerson;
    }

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
}
