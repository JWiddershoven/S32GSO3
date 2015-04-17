/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import java.beans.PropertyChangeEvent;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import remote.RemotePropertyListener;

/**
 *
 * @author Jelle
 */
public class BannerController extends Application implements RemotePropertyListener {

    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;

    @Override
    public void start(Stage primaryStage) {
        banner = new AEXBanner();
        try
        {
            UnicastRemoteObject.exportObject((Remote) this);
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //primaryStage acts as the common stage of the AEXBanner and the 
        //BannerController:
        banner.start(primaryStage);
        //create a timer which polls every 2 seconds
        Timer pollingTimer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String koers = "";
                        try {
                            Registry registry = LocateRegistry.getRegistry("145.93.100.14", 1099);
                            IEffectenbeurs fondsen = (IEffectenbeurs) registry.lookup("Fondsen");
                            effectenbeurs = fondsen;
                            for (IFonds i : effectenbeurs.getKoersen()) {
                                koers += i.getNaam() + " " + i.getKoers() + " ";
                            }
                            banner.setKoersen(koers);
                        } catch (RemoteException ex) {

                        } catch (NotBoundException ex) {
                            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
            }
        };

        pollingTimer.scheduleAtFixedRate(task, 0, 2000);

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        
    }

}
