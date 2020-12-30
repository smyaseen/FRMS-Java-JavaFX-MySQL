package frms.util;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class SceneSelector {

    private static Map<String, Pane> screenMap;
    private static Scene mainScene;


    public SceneSelector(Scene scene) {
        mainScene = scene;
        screenMap = new HashMap<>();
    }

    public void addScreen(String name,Pane pane) {
        screenMap.put(name,pane);
    }

    public void removeScreen(String name) {
        screenMap.remove(name);
    }

    public static void switchScreen(String name) {
        mainScene.setRoot(screenMap.get(name));
    }

    public static void setWidth(double width) {
        mainScene.getWindow().setWidth(width);
    }

}
