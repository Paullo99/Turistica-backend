package pl.turistica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String address;
    private String city;
    private String postcode;
    private String phoneNumber;

    @ManyToOne
    private Role role;

    @ManyToMany(mappedBy = "users")
    Set<Trip> trips = new HashSet<>();

    public User() {}

    public User(String email, String password, String name, String lastName, String phoneNumber, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User(int id, String email, String password, String name, String lastName, String address, String city, String postcode, String phoneNumber, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }
}
