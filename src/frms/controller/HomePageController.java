package frms.controller;

import frms.util.SceneSelector;
import javafx.fxml.FXML;

public class HomePageController {

    // opens show flight gui
    @FXML
    private void openShowFlights() {
        SceneSelector.switchScreen("showFlight");
        SceneSelector.setWidth(1600);
    }


}
