/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author dhahb
 */
public class Switch {
    private static double x = 0;
    private static double y = 0;
    public static void changeToScene(Class aClass,Event aEvent,String SceneFilestr)throws Exception
    {
        Parent root = FXMLLoader.load (aClass.getResource(SceneFilestr));
       Scene scene =new Scene(root);
       Stage stage=(Stage)((Node)aEvent.getSource()).getScene().getWindow();
       
        // Make the window draggable
       root.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });
                    
                    root.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });
                    
                    
       stage.setScene(scene);
       stage.show();
        
    }
}
