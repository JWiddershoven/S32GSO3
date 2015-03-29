/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Jelle
 */
public class BannerController extends Application {

    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;

    @Override
    public void start(Stage primaryStage) {
        banner = new AEXBanner();
        effectenbeurs = new MockEffectenBeurs();
        //primaryStage acts as the common stage of the AEXBanner and the 
        //BannerController:
        banner.start(primaryStage);

        //create a timer which polls every 2 seconds
        Timer pollingTimer = null;
        pollingTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                String koersen = "";
                for (IFonds I : effectenbeurs.getKoersen()) {
                    koersen = koersen + I.toString();
                }
                banner.setKoersen(koersen);
            }
        }, 0, 2000);
        
        //remove pollingTimer as soon as primaryStage is closing:
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            pollingTimer.cancel();
        });
    }

    /**
     * unfortunately, is needed pro forma to act as runnable class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
