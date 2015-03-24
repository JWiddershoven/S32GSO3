/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import javafx.stage.Stage;

/**
 *
 * @author Jelle
 */
public class AEXBanner extends javafx.application.Application {
    
    public AEXBanner()
    {
        
    }
    
    /**
     * Wijzigt de koersen.
     * @param koersen De huidige koersen. Mag geen lege string zijn.
     */
    public void setKoersen(String koersen)
    {
        if (koersen.equals(""))
        {
            throw new IllegalArgumentException("Koersen mag geen lege string zijn!");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
    }
}
