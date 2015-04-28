/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aex.beurs;

import aex.shared.Fonds;
import aex.shared.IEffectenbeurs;
import aex.shared.IFonds;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 * @author Jelle
 */
public class MockEffectenBeurs extends UnicastRemoteObject implements IEffectenbeurs
{

    private List<IFonds> fondsen;
    private BasicPublisher bp;

    /**
     *
     * @throws java.rmi.RemoteException
     */
    public MockEffectenBeurs() throws RemoteException
    {
        koersenTimer();
        bp = new BasicPublisher(new String[]
        {
            "Fondsen"
        });
    }

    public void koersenTimer()
    {
        Timer koersenTimer = new Timer();
        TimerTask task = new TimerTask()
        {

            @Override
            public void run()
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            generateKoersen();
                            bp.inform(this, null, null, fondsen);
                        }
                        catch (RemoteException ex)
                        {
                            Logger.getLogger(MockEffectenBeurs.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        };

        koersenTimer.scheduleAtFixedRate(task, 0, 18000);
    }

    public List<IFonds> generateKoersen() throws RemoteException
    {
        fondsen = new ArrayList<>();
        fondsen.add(new Fonds("Microsoft", generateKoers()));
        fondsen.add(new Fonds("Apple", generateKoers()));
        fondsen.add(new Fonds("Google", generateKoers()));
        fondsen.add(new Fonds("Nokia", generateKoers()));
        fondsen.add(new Fonds("Yahoo", generateKoers()));
        return fondsen;
    }

    @Override
    public List<IFonds> getKoersen() throws RemoteException
    {
        return fondsen;
    }

    public double generateKoers()
    {
        Random r = new Random();
        double koers = r.nextInt(101) + r.nextDouble();
        koers = Math.round(koers * 10);
        koers = koers / 10;
        if (koers < 10)
        {
            generateKoers();
        }
        return koers;
    }

    public static void main(String[] arg)
    {
        JFXPanel fxPanel = new JFXPanel();
        try
        {
            Registry registry = LocateRegistry.createRegistry(1099);
            IEffectenbeurs beurs = new MockEffectenBeurs();
            registry.rebind("beurs", beurs);

        } catch (RemoteException ex)
        {
            System.out.println("Error");
        }
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property)
    {
        bp.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property)
    {
        bp.removeListener(listener, property);
    }

    public void removeAllListeners()
    {
        
    }

   
    public void start(Stage primaryStage) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
