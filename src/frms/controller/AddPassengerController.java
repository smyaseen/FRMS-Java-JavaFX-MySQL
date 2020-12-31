package frms.controller;

import frms.database.DataSource;
import frms.model.Flight;
import frms.model.Passenger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddPassengerController {

     // == fields ==

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


    // supplied flight obj the user selected

    private static Flight flight = null;

    // handle the adding of passenger after user click on add passenger
    @FXML
    public void handleAddPassenger() {

        // check first if seat is even available
        if (flight.getSeatsRemaining() <= 0) {
            showAlert("No more seats remaining!");
            return;
        }

        String alert = "";

        // check for empty fields

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

        if (!alert.isBlank()) {
            showAlert(alert);
            return;
        }

        // check for correct format

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


        Passenger passenger = new Passenger(name.getText().trim(),Integer.parseInt(age.getText()),nationality.getText().trim(),
                idNo.getText().trim(),passportNo.getText().trim());

        try {

            // adding in DB
            DataSource.getInstance().addPassenger(flight.getFlightCode(),passenger);

            // adding in DS
            flight.getPassengers().insertPassenger(passenger);

            // adding 1 to seats occupied
            flight.setSeatsOccupied(1);

            //subtracting 1 from seats remaining
            flight.setSeatsRemaining(-1);

            // update seats info in db
            DataSource.getInstance().updateFlightSeats(flight.getFlightCode(),
                    flight.getSeatsOccupied(),flight.getSeatsRemaining());


        } catch (Exception e) {
            showAlert(e.getMessage());
            return;
        }

        // add in gui
        ShowPassengersController.passengers.add(passenger);

        try {
            // basically performing refreshing gui by removing and adding
            ShowFlightController.flights.remove(flight);
            ShowFlightController.flights.add(flight);

        } catch (Exception e) {
            showAlert(e.getMessage());
        }

        showAlert("Passenger Added!");
    }

// show alerts with supplied problem
    private void showAlert(String text) {

        Alert inputAlert;

        // if certain text, show info
        if (text == "Passenger Added!") {
            inputAlert = new Alert(Alert.AlertType.INFORMATION);
            inputAlert.setHeaderText("Success!");
        }

        // show error
        else {
            inputAlert = new Alert(Alert.AlertType.ERROR);
            inputAlert.setHeaderText("input error");
        }

        // make current dialog owner
        inputAlert.initOwner(nationality.getScene().getWindow());

        inputAlert.setContentText(text);

        //show and wait for further instructions
        inputAlert.showAndWait();

    }

    // supplied user selected flight obj
    public void process(Flight flight) {
        if (flight != null)
            this.flight = flight;
    }

}
