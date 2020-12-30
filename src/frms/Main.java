package frms;

import frms.database.DataSource;
import frms.util.SceneSelector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Pane homePage = FXMLLoader.load(getClass().getResource("view/HomePage.fxml"));

        primaryStage.setScene(new Scene(homePage, 800, 500));
        primaryStage.setTitle("Flight Reservation Management System");
        primaryStage.show();

        SceneSelector sceneSelector = new SceneSelector(primaryStage.getScene());

        Pane addFlight = FXMLLoader.load(getClass().getResource("view/AddFlight.fxml"));
        Pane addPassenger = FXMLLoader.load(getClass().getResource("view/AddPassenger.fxml"));
        Pane showFlight = FXMLLoader.load(getClass().getResource("view/ShowFlight.fxml"));
        Pane showPassengers = FXMLLoader.load(getClass().getResource("view/ShowPassengers.fxml"));
        Pane updateFlight = FXMLLoader.load(getClass().getResource("view/UpdateFlight.fxml"));


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

    @Override
    public void init() {

        if (!(DataSource.getInstance().openDb() && DataSource.getInstance().loadAllFlights())) {
            System.out.println("error opening database exiting application..");
            Platform.exit();
        }

        DataSource.getInstance().loadAllPassengers();

        if(!(frms.controller.showFlightController.loadAllFlightsGUI())) {

            System.out.println("error loading Flights in GUI exiting application..");
            Platform.exit();
        }

    }

    @Override
    public void stop() {
        DataSource.getInstance().closeDb();
        Platform.exit();
    }


}
