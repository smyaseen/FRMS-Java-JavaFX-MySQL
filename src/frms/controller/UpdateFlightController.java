package frms.controller;

import frms.database.DataSource;
import frms.model.Flight;
import frms.model.FlightSLDS;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateFlightController {

    // == fields ==

    @FXML
    private TextField airlineName;

    @FXML
    private TextField flightCode;

    @FXML
    private TextField origin;

    @FXML
    private TextField fare;

    @FXML
    private TextField seats;

    @FXML
    private TextField destination;

    @FXML
    private TextField departureTime;

    @FXML
    private TextField arrivalTime;

    @FXML
    private DatePicker date;

    // user selected flight obj
    private Flight flight;

    // method to set selected flight obj
    public void process(Flight flight) {
        this.flight = flight;

        // pre setting fields with current data
        airlineName.setText(flight.getAirlineName());
        flightCode.setText(flight.getFlightCode());
        origin.setText(flight.getOrigin());
        fare.setText("" + flight.getFare());
        departureTime.setText(flight.getDestination());
        arrivalTime.setText(flight.getArrivalTime());
        departureTime.setText(flight.getDepartureTime());
        seats.setText("" + flight.getTotalSeats());
        destination.setText(flight.getDestination());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(flight.getDate(), formatter);
        date.setValue(localDate);
    }


    // updates flight after user click update flight button
    @FXML
    public void updateFlight() {

     String alert = "";

     // check if blank
        try {

            if (airlineName.getText().isBlank())
            alert = "Flight name must not be empty!\n";
        if (flightCode.getText().isBlank())
            alert  += "Flight code must not be empty!\n";
        if (origin.getText().isBlank())
            alert += "Origin must not be empty!\n";
        if (destination.getText().isBlank())
            alert += "Destination must not be empty!\n";
        if (arrivalTime.getText().isBlank())
            alert += "Arrival Time must not be empty!\n";
        if (departureTime.getText().isBlank())
            alert += "Departure Time must not be empty!\n";
        if (seats.getText().isBlank())
            alert += "seats must not be empty!\n";
        if (fare.getText().isBlank())
            alert += "fare must not be empty!\n";
        if (date.getValue() == null)
            alert += "Date must not be empty!";

        if (!alert.isBlank()) {
            showAlert(alert);
            return;
        }

        // check for right format
        if (!airlineName.getText().trim().matches("[A-Za-z\\s]+"))
            alert += "Flight name must be alphabets only!\n";
        if (!flightCode.getText().trim().matches("^[A-Za-z0-9]+$"))
            alert  += "Flight code must be alphabets and numbers only no spaces!\n";
        if (!origin.getText().trim().matches("[A-Za-z\\s]+"))
            alert += "Origin must be alphabets only and no spaces!\n";
        if (!destination.getText().trim().matches("[A-Za-z\\s]+"))
            alert += "Destination must be alphabets only no spaces!\n";
        if (!arrivalTime.getText().trim().matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"))
            alert += "Arrival Time must be 24h format (hh:mm) only!\n";
        if (!departureTime.getText().trim().matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"))
            alert += "Departure Time must be 24h format (hh:mm) only!\n";
        if (!seats.getText().trim().matches("^[+]?([1-9][0-9]*(?:[\\.][0-9]*)?|0*\\.0*[1-9][0-9]*)(?:[eE][+-][0-9]+)?$"))
            alert += "seats must be numbers & > 0\n";
        if (!fare.getText().trim().matches("^[+]?([1-9][0-9]*(?:[\\.][0-9]*)?|0*\\.0*[1-9][0-9]*)(?:[eE][+-][0-9]+)?$"))
            alert += "fare must be numbers & > 0";

        if (!alert.isBlank()) {
            showAlert(alert);
            return;
        }

        // check if something is even changed or not
            if (flight.getAirlineName().equalsIgnoreCase(airlineName.getText().trim()) &&
                    flight.getFlightCode().equalsIgnoreCase(flightCode.getText().trim()) &&
                    flight.getOrigin().equalsIgnoreCase(origin.getText().trim()) &&
                    flight.getDestination().equalsIgnoreCase(destination.getText().trim()) &&
                    flight.getDepartureTime().equalsIgnoreCase(departureTime.getText().trim()) &&
                    flight.getArrivalTime().equalsIgnoreCase(arrivalTime.getText().trim()) &&
                    flight.getTotalSeats()  == Double.parseDouble(seats.getText().trim()) &&
                    flight.getFare() == Double.parseDouble(fare.getText().trim()) &&
                    flight.getDate().equalsIgnoreCase(date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyy")))) {

                showAlert("nothing changed!");
                return;
            }

            // check if the updated flight code is already available or not
        if (!flight.getFlightCode().equals(flightCode.getText())) {
            for (Flight i = FlightSLDS.getInstance().getHead(); i != null ; i = i.getNext()) {
                    if (i.getFlightCode().equalsIgnoreCase(flightCode.getText())) {
                        showAlert("Flight with same code Already Present!");
                        return;
                    }
            }
        }


            //checking date

            if (LocalDate.now().compareTo(LocalDate.of(date.getValue().getYear(),
                    date.getValue().getMonth(),date.getValue().getDayOfMonth())) > 0) {
                showAlert("Date must be of today or future");
                return;
            }

        Flight updateFlight = new Flight(airlineName.getText().trim(),flightCode.getText().trim(),origin.getText().trim(),destination.getText().trim(),
                arrivalTime.getText().trim(),departureTime.getText().trim(),Integer.parseInt(seats.getText().trim()),
                Double.parseDouble(fare.getText().trim()),date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyy")));


            // updating seats
            updateFlight.setSeatsRemaining(-(flight.getTotalSeats() - flight.getSeatsRemaining()));
            updateFlight.setSeatsOccupied(flight.getSeatsOccupied());
            updateFlight.setPassengers(flight.getPassengers());

            FlightSLDS.getInstance().update(flight,updateFlight);

            // flight code changed then change the passengers flight code from DB
            if (!flight.getFlightCode().equals(flightCode.getText()))
                DataSource.getInstance().updatePassengersFlightCode(flight.getFlightCode(),
                        flightCode.getText());

            // delete from db
                DataSource.getInstance().deleteFlight(flight.getFlightCode(),false);

                // add in db
            DataSource.getInstance().addFlight(updateFlight);

            // refresh gui
            showFlightController.flights.remove(flight);
            showFlightController.flights.add(updateFlight);
            this.flight = updateFlight;
            showAlert("Flight Updated!");

        } catch (Exception e) {
            showAlert(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    // shows alert with supplied problem
    private void showAlert(String text) {

        Alert inputAlert;

        if (text == "Flight Updated!") {
            inputAlert = new Alert(Alert.AlertType.INFORMATION);
            inputAlert.setHeaderText("Success!");
        }
        else {
            inputAlert = new Alert(Alert.AlertType.ERROR);
            inputAlert.setHeaderText("input error");
        }

        inputAlert.initOwner(airlineName.getScene().getWindow());

        inputAlert.setContentText(text);

        inputAlert.showAndWait();


    }



}
