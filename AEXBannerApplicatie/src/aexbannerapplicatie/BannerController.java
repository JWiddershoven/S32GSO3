/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Jelle
 */
public class BannerController extends Application {
    
    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        banner = new AEXBanner();
        banner.start(new Stage());
        effectenbeurs = new MockEffectenBeurs();
    }
}
