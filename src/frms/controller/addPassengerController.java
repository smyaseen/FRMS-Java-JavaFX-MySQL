package frms.controller;

import frms.database.DataSource;
import frms.model.Flight;
import frms.model.Passenger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;

public class addPassengerController {

    @FXML
    private TextField name;

    @FXML
    private TextField nationality;

    @FXML
    private TextField age;

    @FXML
    private TextField idNo;

    @FXML
    private TextField passportNo;

    @FXML
    private DatePicker dOB;

    private static Flight flight = null;

    @FXML
    public void handleAddPassenger() {

        if (flight.getSeatsRemaining() <= 0) {
            showAlert("No more seats remaining!");
            return;
        }

        String alert = "";


        if (name.getText().isBlank())
            alert = "name must not be empty!\n";
        if (nationality.getText().isBlank())
            alert  += "Nationality must not be empty!\n";
        if (idNo.getText().isBlank())
            alert += "ID Number must not be empty!\n";
        if (passportNo.getText().isBlank())
            alert += "Passport No. must not be empty!\n";
        if (age.getText().isBlank())
            alert += "Age must not be empty!\n";
        if (dOB.getValue() == null)
            alert += "Date must not be empty!";

        if (!alert.isBlank()) {
            showAlert(alert);
            return;
        }

        if (!name.getText().trim().matches("[A-Za-z\\s]+"))
            alert += "Name must be alphabets only!\n";
        if (!nationality.getText().trim().matches("^[A-Za-z]+$"))
            alert  += "Nationality must be alphabets only no spaces!\n";
        if (!idNo.getText().trim().matches("^\\+?(0|[1-9]\\d*)$") || idNo.getText().length() != 13)
            alert += "ID No. must be numbers and 13 digits only!\n";
        if (!passportNo.getText().trim().matches("[A-Za-z0-9]+"))
            alert += "Passport No. must be alphabets and numbers only!\n";
        if (age.getText().trim().matches("[0-9]+"))  {

            if (Integer.parseInt(age.getText()) <= 0)
                alert += "Age must be whole numbers >0 only!\n";

        } else alert += "Age must be whole numbers >0 only!\n";

        if (!alert.isBlank()) {
            showAlert(alert);
            return;
        }


        Passenger passenger = new Passenger(name.getText().trim(),Integer.parseInt(age.getText()),dOB.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyy")),nationality.getText().trim(),
                idNo.getText().trim(),passportNo.getText().trim());

        try {

            DataSource.getInstance().addPassenger(flight.getFlightCode(),passenger);

            flight.getPassengers().insertPassenger(passenger);
            flight.setSeatsOccupied(1);
            flight.setSeatsRemaining(-1);

            DataSource.getInstance().updateFlightSeats(flight.getFlightCode(),
                    flight.getSeatsOccupied(),flight.getSeatsRemaining());


        } catch (Exception e) {
            showAlert(e.getMessage());
            return;
        }

        showPassengersController.passengers.add(passenger);

        try {

            showFlightController.flights.remove(flight);
            showFlightController.flights.add(flight);

        } catch (Exception e) {
            showAlert(e.getMessage());
        }

        showAlert("Passenger Added!");
    }


    private void showAlert(String text) {

        Alert inputAlert;

        if (text == "Passenger Added!") {
            inputAlert = new Alert(Alert.AlertType.INFORMATION);
            inputAlert.setHeaderText("Success!");
        }
        else {
            inputAlert = new Alert(Alert.AlertType.ERROR);
            inputAlert.setHeaderText("input error");
        }

        inputAlert.initOwner(nationality.getScene().getWindow());

        inputAlert.setContentText(text);

        inputAlert.showAndWait();

    }

    public void process(Flight flight) {
        if (flight != null)
            this.flight = flight;
    }

}
