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

    private Flight flight;

    public void process(Flight flight) {
        this.flight = flight;

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

    @FXML
    public void updateFlight() {

        String alert = "";


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

        if (!airlineName.getText().trim().matches("[A-Za-z\\s]+"))
            alert += "Flight name must be alphabets only!\n";
        if (!flightCode.getText().trim().matches("^[A-Za-z0-9]+$"))
            alert  += "Flight code must be alphabets and numbers only no spaces!\n";
        if (!origin.getText().trim().matches("^[A-Za-z]+$"))
            alert += "Origin must be alphabets only and no spaces!\n";
        if (!destination.getText().trim().matches("^[A-Za-z]+$"))
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


        Flight updateFlight = new Flight(airlineName.getText().trim(),flightCode.getText().trim(),origin.getText().trim(),destination.getText().trim(),
                arrivalTime.getText().trim(),departureTime.getText().trim(),Integer.parseInt(seats.getText().trim()),
                Double.parseDouble(fare.getText().trim()),date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyy")));

        try {
            updateFlight.setSeatsRemaining(-(flight.getTotalSeats() - flight.getSeatsRemaining()));
            updateFlight.setSeatsOccupied(flight.getSeatsOccupied());
            updateFlight.setPassengers(flight.getPassengers());

            FlightSLDS.getInstance().update(flight,updateFlight);

            if (!flight.getFlightCode().equals(flightCode.getText()))
                DataSource.getInstance().updatePassengersFlightCode(flight.getFlightCode(),
                        flightCode.getText());

                DataSource.getInstance().deleteFlight(flight.getFlightCode(),false);

            DataSource.getInstance().addFlight(updateFlight);

        } catch (Exception e) {
            showAlert(e.getMessage());
            System.out.println(e.getMessage());
            return;
        }

        showFlightController.flights.remove(flight);
        showFlightController.flights.add(updateFlight);
        showAlert("Flight Updated!");
    }


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
