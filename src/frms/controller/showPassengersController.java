package frms.controller;

import frms.model.Flight;
import frms.model.Passenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class showPassengersController {

    // == fields ==

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Passenger> showPassengersTable;

    @FXML
    private TableColumn<Passenger,String> name;

    @FXML
    private TableColumn<Passenger,String> idNo;

    @FXML
    private TableColumn<Passenger,Integer> age;

    @FXML
    private TableColumn<Passenger,String> dOB;

    @FXML
    private TableColumn<Passenger,Boolean> needSpecialAssistance;

    @FXML
    private TableColumn<Passenger,String> passportNo;

    @FXML
    private TableColumn<Passenger,String> nationality;

    // ds for gui
    public static final ObservableList<Passenger> passengers = FXCollections.observableArrayList();

    // supplied obj of user selected flight
    private static Flight flight;

    @FXML
    private Label passengerToDisplay;


// call this method when gui passengers opens open
    public void initialize() {

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        passportNo.setCellValueFactory(new PropertyValueFactory<>("passportNo"));
        idNo.setCellValueFactory(new PropertyValueFactory<>("idNo"));
        nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        needSpecialAssistance.setCellValueFactory(new PropertyValueFactory<>("needSpecialAssistance"));
        dOB.setCellValueFactory(new PropertyValueFactory<>("dOB"));

        showPassengersTable.setItems(passengers);
    }

    // set supplied obj of user selected flight
    public void processTable(Flight flight) {


        try {

            // clear if previously have data
            passengers.clear();

            this.flight = flight;

            // add passengers from ds to gui ds
            if (!flight.getPassengers().isEmpty()) {
                for (Passenger i = flight.getPassengers().getFirst(); i != null; i = i.getNext()) {
                    passengers.add(i);
                }
            }

        } catch (Exception e) {
            showAlert(e.getMessage(),"Show Passengers  error!", Alert.AlertType.ERROR);
        }
    }

    // show alerts of different kinds
    private ButtonType showAlert(String content, String header, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.initOwner(showPassengersTable.getScene().getWindow());
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(null);

    }

    // opens add passenger dialog
    @FXML
    public void openAddPassenger() {

        if (flight.getSeatsRemaining() <= 0) {
            showAlert("No more seats remaining!","Book Passenger error!", Alert.AlertType.ERROR);
            return;
        }


        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/frms/view/AddPassenger.fxml"));

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            showAlert(e.getMessage(),"dialog open error!", Alert.AlertType.ERROR);
            return;
        }

        addPassengerController addPassengerController = fxmlLoader.getController();
        addPassengerController.process(flight);


        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.show();

    }

    // deletes selected passenger user selected
    @FXML
    public void deletePassenger() {

        Passenger passengerToDelete = showPassengersTable.getSelectionModel().getSelectedItem();

        if (passengerToDelete == null) {
            return;
        }


        if (!(showAlert("are you sure to delete: " + passengerToDelete.getName() + "\nid: "
                        + passengerToDelete.getIdNo() + "\npassport: "
                        + passengerToDelete.getPassportNo(),
                "Delete Item Alert!",
                Alert.AlertType.CONFIRMATION) == ButtonType.OK))

            return;

        try {

            flight.getPassengers().removePassenger(flight.getFlightCode(),passengerToDelete);

            showAlert("Passenger: " + passengerToDelete.getName() + "\nid: "
                            + passengerToDelete.getIdNo() + "\npassport: "
                            + passengerToDelete.getPassportNo() + "\ndeleted!","Passenger Deleted"
                    , Alert.AlertType.INFORMATION);

            passengers.remove(passengerToDelete);

            flight.setSeatsOccupied(-1);
            flight.setSeatsRemaining(1);

            showFlightController.flights.remove(flight);
            showFlightController.flights.add(flight);


        } catch (Exception e) {
            showAlert("Passenger: " + passengerToDelete.getName() + "\nid: "
                            + passengerToDelete.getIdNo() + "\npassport: "
                            + passengerToDelete.getPassportNo() + "\ncould not be deleted!",e.getMessage()
                    , Alert.AlertType.ERROR);
        }

    }

    // display label of passenger user selected
    @FXML
    public void displayPassenger() {

        try {

            Passenger passenger = showPassengersTable.getSelectionModel().getSelectedItem();

            passengerToDisplay.setText(passenger.getName() + " - " + passenger.getPassportNo() + " -" + passenger.getIdNo());

        } catch (Exception e) {
            // do nothing
        }

    }


}
