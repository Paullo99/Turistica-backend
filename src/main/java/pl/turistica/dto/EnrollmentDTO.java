package pl.turistica.dto;

public class EnrollmentDTO {

    private boolean isEnrolled;

    private int enrolledPeople;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(boolean isEnrolled, int enrolledPeople) {
        this.isEnrolled = isEnrolled;
        this.enrolledPeople = enrolledPeople;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
    }

    public int getEnrolledPeople() {
        return enrolledPeople;
    }

    public void setEnrolledPeople(int enrolledPeople) {
        this.enrolledPeople = enrolledPeople;
    }
}
