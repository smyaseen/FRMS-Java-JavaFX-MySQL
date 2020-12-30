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

    protected static final ObservableList<Flight> flights = FXCollections.observableArrayList();

    public static boolean loadAllFlightsGUI() {

        try {

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

    public void initialize() {

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

        showFlightTable.setItems(flights);

        showFlightTable.getSelectionModel().selectFirst();

    }


    @FXML
    private void backToHomePage() {
        SceneSelector.switchScreen("homePage");
        SceneSelector.setWidth(850);

    }

    @FXML
    public void openAddFlight() {

        openDialog("/frms/view/AddFlight.fxml");

    }

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

    @FXML
    public void openUpdateFlight() {

        if (showFlightTable.getSelectionModel().getSelectedItem() == null)
            return;



        UpdateFlightController updateFlightController =
                (UpdateFlightController)openDialog("/frms/view/updateFlight.fxml");

        updateFlightController.process(showFlightTable.getSelectionModel().getSelectedItem());

    }

    @FXML
    public void openShowPassengers() {

        if (showFlightTable.getSelectionModel().getSelectedItem() == null)
            return;

        showPassengersController showPassengersController =
                (showPassengersController) openDialog("/frms/view/showPassengers.fxml");

        showPassengersController.processTable(showFlightTable.getSelectionModel().getSelectedItem());

    }

    @FXML
    public void deleteFlight() {

        Flight flightToDelete = showFlightTable.getSelectionModel().getSelectedItem();

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

    private ButtonType showAlert(String content, String header, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.initOwner(showFlightTable.getScene().getWindow());
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(null);

    }

    @FXML
    public void displayFlight() {

        try {

            Flight flight = showFlightTable.getSelectionModel().getSelectedItem();

            flightToDisplay.setText(flight.getAirlineName() + " - " + flight.getFlightCode());

        } catch (Exception e) {
            // do nothing
        }

    }

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

    @FXML
    public void showAllFlights() {

        if (showFlightTable.getItems() == flights)
            return;

        showFlightTable.setItems(flights);
    }

}
