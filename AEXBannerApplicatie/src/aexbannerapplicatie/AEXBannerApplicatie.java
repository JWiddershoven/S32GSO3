/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jelle
 */
public class AEXBannerApplicatie extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    AnimationTimer timer = new AnimationTimer() {
    private long prevUpdate;

    @Override public void handle(long now) {
        long lag = now - prevUpdate;
        if (lag >= 20000000) {
            // Update JavaFX Scene Graph
            prevUpdate = now;
        }
    }
    @Override public void start() {
        prevUpdate = System.nanoTime();
        super.start();
    }
    
    
};
    
    
}