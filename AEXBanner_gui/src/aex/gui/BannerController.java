/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aex.gui;

import aex.shared.IFonds;
import aex.shared.IEffectenbeurs;
import java.beans.PropertyChangeEvent;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import fontys.observer.RemotePropertyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jelle
 */
public class BannerController extends Application implements RemotePropertyListener
{

    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs = null;

    @Override
    public void start(Stage primaryStage) throws RemoteException
    {
        banner = new AEXBanner();
        
        try
        {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException ex)
        {
            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            Registry registry = LocateRegistry.getRegistry("145,93.96.75", 1099);
            effectenbeurs = (IEffectenbeurs) registry.lookup("beurs");
            effectenbeurs.addListener(this, "Fondsen");
        } catch (NotBoundException | AccessException ex)
        {
            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        banner.start(primaryStage);
    }

    /**
     * unfortunately, is needed pro forma to act as runnable class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        String koersen = "";
        for (IFonds fond : (List<IFonds>) evt.getNewValue())
        {
            koersen = koersen + " " + fond.getNaam() + " " + fond.getKoers();
        }
        System.out.println(koersen);
        banner.setKoersen(koersen);
    }

}
