package frms;

import frms.controller.ShowFlightController;
import frms.database.DataSource;
import frms.util.SceneSelector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    // the method called when app starts
    @Override
    public void start(Stage primaryStage) throws Exception{

        // making pane obj
        Pane homePage = FXMLLoader.load(getClass().getResource("view/HomePage.fxml"));

        // setting the main stage
        primaryStage.setScene(new Scene(homePage, 800, 500));
        primaryStage.setTitle("Flight Reservation Management System");
        primaryStage.show();

        // the class to change scenes intuitively
        SceneSelector sceneSelector = new SceneSelector(primaryStage.getScene());

        // making objs of all views
        Pane addFlight = FXMLLoader.load(getClass().getResource("view/AddFlight.fxml"));
        Pane addPassenger = FXMLLoader.load(getClass().getResource("view/AddPassenger.fxml"));
        Pane showFlight = FXMLLoader.load(getClass().getResource("view/ShowFlight.fxml"));
        Pane showPassengers = FXMLLoader.load(getClass().getResource("view/ShowPassengers.fxml"));
        Pane updateFlight = FXMLLoader.load(getClass().getResource("view/UpdateFlight.fxml"));

        // adding all views objs to scene selector collection
        sceneSelector.addScreen("homePage",homePage);
        sceneSelector.addScreen("addFlight",addFlight);
        sceneSelector.addScreen("addPassenger",addPassenger);
        sceneSelector.addScreen("showFlight",showFlight);
        sceneSelector.addScreen("showPassengers",showPassengers);
        sceneSelector.addScreen("updateFlight",updateFlight);

    }


    public static void main(String[] args) {
        launch(args);
    }

    // the method first that is called when app starts
    @Override
    public void init() {

        // check if successfully opens DB and loaded all flights from db to DS
        if (!(DataSource.getInstance().openDb() && DataSource.getInstance().loadAllFlights())) {
            System.out.println("error opening database exiting application..");
            Platform.exit();
        }

        // loads all passengers from db to DS
        DataSource.getInstance().loadAllPassengers();

        // setting flights in gui
        if(!(ShowFlightController.loadAllFlightsGUI())) {

            System.out.println("error loading Flights in GUI exiting application..");
            Platform.exit();
        }

    }

    // the last method that called
    @Override
    public void stop() {
        // close DB first
        DataSource.getInstance().closeDb();
        Platform.exit();
    }


}
