package frms.controller;

import frms.database.DataSource;
import frms.model.Flight;
import frms.model.FlightSLDS;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;

public class addFlightController {

    @FXML
    private TextField airlineName;

    @FXML
    private TextField flightCode;

    @FXML
    private TextField origin;

    @FXML
    private TextField destination;

    @FXML
    private TextField fare;

    @FXML
    private TextField seats;

    @FXML
    private TextField arrivalTime;

    @FXML
    private TextField departureTime;

    @FXML
    private DatePicker date;

    @FXML
    private Button addFlight;

    @FXML
    public void handleAddFlight() {

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


        Flight flight = new Flight(airlineName.getText().trim(),flightCode.getText().trim(),origin.getText().trim(),destination.getText().trim()
                ,arrivalTime.getText().trim(),departureTime.getText().trim(),Integer.parseInt(seats.getText().trim()),
                Double.parseDouble(fare.getText().trim()),date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyy")));

        try {

            FlightSLDS.getInstance().addFlight(flight);

            DataSource.getInstance().addFlight(flight);

        } catch (Exception e) {
            showAlert(e.getMessage());
            return;
        }

        showFlightController.flights.add(flight);
        showAlert("Flight Added!");
    }


    private void showAlert(String text) {

        Alert inputAlert;

        if (text == "Flight Added!") {
            inputAlert = new Alert(Alert.AlertType.INFORMATION);
            inputAlert.setHeaderText("Success!");
        }
        else {
             inputAlert = new Alert(Alert.AlertType.ERROR);
            inputAlert.setHeaderText("input error");
        }

        inputAlert.initOwner(addFlight.getScene().getWindow());

        inputAlert.setContentText(text);

        inputAlert.showAndWait();


    }

}
