package frms.controller;

import frms.database.DataSource;
import frms.model.Flight;
import frms.model.FlightSLDS;
import frms.util.SceneSelector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

public class showFlightController {

    // == fields ==

    @FXML
    private TableView<Flight> showFlightTable;

    @FXML
    private TableColumn<Flight,String> airlineName;

    @FXML
    private TableColumn<Flight,String> flightCode;

    @FXML
    private TableColumn<Flight,String> origin;

    @FXML
    private TableColumn<Flight,String> destination;

    @FXML
    private TableColumn<Flight,String> departureTime;

    @FXML
    private TableColumn<Flight,String> arrivalTime;

    @FXML
    private TableColumn<Flight,Double> fare;

    @FXML
    private TableColumn<Flight,Integer> totalSeats;

    @FXML
    private TableColumn<Flight,Integer> seatsRemaining;

    @FXML
    private TableColumn<Flight,Integer> seatsOccupied;

    @FXML
    private TableColumn<Flight,String> date;

    @FXML
    private TextField searchOrigin;

    @FXML
    private TextField searchDestination;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label flightToDisplay;

    // flights storage for gui
    protected static final ObservableList<Flight> flights = FXCollections.observableArrayList();

    // after initializing of app , loads flights from DS to gui
    public static boolean loadAllFlightsGUI() {

        try {

            // check if have flights
            if (!FlightSLDS.getInstance().isEmpty()) {
                for (Flight i = FlightSLDS.getInstance().getHead(); i != null; i = i.getNext()) {

                    flights.add(i);

                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // method call when show flight gui shows
    public void initialize() {

        // setting gui table with flight fields

        airlineName.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        flightCode.setCellValueFactory(new PropertyValueFactory<>("flightCode"));
        origin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        arrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        departureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        fare.setCellValueFactory(new PropertyValueFactory<>("fare"));
        totalSeats.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        seatsRemaining.setCellValueFactory(new PropertyValueFactory<>("seatsRemaining"));
        seatsOccupied.setCellValueFactory(new PropertyValueFactory<>("seatsOccupied"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        // setting table in gui with flight data
        showFlightTable.setItems(flights);

        // if available select the first flight when app launches
        showFlightTable.getSelectionModel().selectFirst();

    }


    // goes back to homepage when user clicks back button
    @FXML
    private void backToHomePage() {
        SceneSelector.switchScreen("homePage");
        SceneSelector.setWidth(850);

    }

    // opens add flight dialog when user presses add flight
    @FXML
    public void openAddFlight() {

        openDialog("/frms/view/AddFlight.fxml");

    }

    // made single method to open dialog of different kinds
    public Object openDialog(String controllerPath) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(controllerPath));

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            showAlert(e.getMessage(),"dialog open error!", Alert.AlertType.ERROR);
            return null;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.show();

        return fxmlLoader.getController();
    }

    // opens update flight dialog when user presses add flight
    @FXML
    public void openUpdateFlight() {

        // if nothing selected then returns
        if (showFlightTable.getSelectionModel().getSelectedItem() == null)
            return;


        // getting instance of opened dialog
        UpdateFlightController updateFlightController =
                (UpdateFlightController)openDialog("/frms/view/updateFlight.fxml");

        // supplying instance of opened dialog to update flight controller
        updateFlightController.process(showFlightTable.getSelectionModel().getSelectedItem());

    }

    // opens add show passengers dialog when user presses add flight
    @FXML
    public void openShowPassengers() {

        if (showFlightTable.getSelectionModel().getSelectedItem() == null)
            return;

        showPassengersController showPassengersController =
                (showPassengersController) openDialog("/frms/view/showPassengers.fxml");

        showPassengersController.processTable(showFlightTable.getSelectionModel().getSelectedItem());

    }

    // deletes selected flight
    @FXML
    public void deleteFlight() {

        Flight flightToDelete = showFlightTable.getSelectionModel().getSelectedItem();
        // check if something selected
        if (flightToDelete == null) {
            return;
        }


        if (!(showAlert("are you sure to delete: " + flightToDelete.getFlightCode(),
                "Delete Item Alert!",
                Alert.AlertType.CONFIRMATION) == ButtonType.OK))

            return;

        try {

            FlightSLDS.getInstance().delete(flightToDelete);

                showAlert("Flight: " + flightToDelete.getFlightCode() + " deleted!","Flight Deleted"
                        , Alert.AlertType.INFORMATION);

                flights.remove(flightToDelete);

            DataSource.getInstance().deleteFlight(flightToDelete.getFlightCode(),true);

        } catch (Exception e) {
            showAlert("Flight: " + flightToDelete.getFlightCode() + " could not be deleted!",e.getMessage()
                    , Alert.AlertType.ERROR);
        }

    }

    // alert methods to show different kinds of alerts
    private ButtonType showAlert(String content, String header, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.initOwner(showFlightTable.getScene().getWindow());
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(null);

    }

    // display selected flight label
    @FXML
    public void displayFlight() {

        try {

            Flight flight = showFlightTable.getSelectionModel().getSelectedItem();

            flightToDisplay.setText(flight.getAirlineName() + " - " + flight.getFlightCode());

        } catch (Exception e) {
            // do nothing
        }

    }

    // search flight and show on gui
    @FXML
    public void searchFlight() {

        if (searchOrigin.getText().isBlank() || searchDestination.getText().isBlank()) {
                return;
        }
        else if (flights.isEmpty())
            return;


        String alert = "";

        if (!searchOrigin.getText().trim().matches("^[A-Za-z]+$"))
            alert += "Origin must be alphabets only and no spaces!\n";
        if (!searchDestination.getText().trim().matches("^[A-Za-z]+$"))
            alert += "Destination must be alphabets only and no spaces!\n";

        if (!alert.isBlank()) {
            showAlert(alert,"Search Error!", Alert.AlertType.ERROR);
            return;
        }

        try {

            Flight searchedFlights =
                  FlightSLDS.getInstance().search(searchOrigin.getText(),searchDestination.getText());


            if (searchedFlights == null)
                throw new Exception("No Flights Available on provided route!");

            ObservableList<Flight> searchedflightsList = FXCollections.observableArrayList();


                for (Flight i = searchedFlights; i != null; i = i.getNext()) {
                    searchedflightsList.add(i);
                }

            showFlightTable.setItems(searchedflightsList);

        } catch (Exception e) {
            showAlert(e.getMessage(),"Search route Not found!", Alert.AlertType.INFORMATION);
        }

    }

    // show all flights available on gui
    @FXML
    public void showAllFlights() {

        if (showFlightTable.getItems() == flights)
            return;

        showFlightTable.setItems(flights);
    }

}
