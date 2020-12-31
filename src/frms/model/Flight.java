package frms.model;

public class Flight {

    // == fields ==

    private String airlineName;
    private String flightCode;
    private String origin;
    private String destination;
    private String arrivalTime;
    private String departureTime;
    private int totalSeats;
    private int seatsRemaining;
    private int seatsOccupied;
    private double fare;
    private String date;
    private PassengerList passengers;
    private Flight next;
    private Flight previous;

    // == constructor ==

    public Flight(String airlineName, String flightCode, String origin, String destination, String arrivalTime, String departureTime, int totalSeats, double fare, String date) {
        this.airlineName = airlineName;
        this.flightCode = flightCode.toUpperCase();
        this.origin = origin;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.seatsRemaining = totalSeats;
        this.seatsOccupied = 0;
        this.fare = fare;
        this.date = date;
        this.passengers = new PassengerList();
        next = null;
        previous = null;
    }

    // == methods ==

    public PassengerList getPassengers() {
        return passengers;
    }

    public void setPassengers(PassengerList passengers) {
        this.passengers = passengers;
    }

    public int getSeatsRemaining() {
        return seatsRemaining;
    }

    public void setSeatsRemaining(int seatsRemaining) {
        this.seatsRemaining += seatsRemaining;
    }

    public int getSeatsOccupied() {
        return seatsOccupied;
    }

    public void setSeatsOccupied(int seatsOccupied) {
        this.seatsOccupied += seatsOccupied;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Flight getNext() {
        return next;
    }

    public void setNext(Flight next) {
        this.next = next;
    }

    public void setPrevious(Flight previous) {
        this.previous = previous;
    }

    public Flight getPrevious() {
        return previous;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

}

