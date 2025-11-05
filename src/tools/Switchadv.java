/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author dhahb
 */

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Switchadv{
    private static double x = 0;
    private static double y = 0;

    // Method for switching scenes with an Event
    public static void changeToScene(Class aClass, Event aEvent, String SceneFilestr) throws Exception {
        Parent root = FXMLLoader.load(aClass.getResource(SceneFilestr));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();

        makeWindowDraggable(root, stage);
        stage.setScene(scene);
        stage.show();
    }

    // Overloaded method for switching scenes without an Event
    public static void changeToSceneWithoutEvent(Class aClass, Stage stage, String SceneFilestr) throws Exception {
        if (stage == null) {
        throw new IllegalArgumentException("Stage is null. Please provide a valid Stage.");
    }
        Parent root = FXMLLoader.load(aClass.getResource(SceneFilestr));
        Scene scene = new Scene(root);

        makeWindowDraggable(root, stage);
        stage.setScene(scene);
        stage.show();
    }

    // Utility method to make the window draggable
    private static void makeWindowDraggable(Parent root, Stage stage) {
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }
}