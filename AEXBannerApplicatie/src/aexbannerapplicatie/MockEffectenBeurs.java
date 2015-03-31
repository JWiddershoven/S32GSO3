/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 *
 * @author Jelle
 */
public class MockEffectenBeurs extends UnicastRemoteObject implements IEffectenbeurs {

    private IFonds[] fondsen;

    /**
     *
     * @throws java.rmi.RemoteException
     */
    public MockEffectenBeurs() throws RemoteException {
        koersenTimer();
    }

    public void koersenTimer() {
        Timer koersenTimer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        
                        try {
                            Registry registry = LocateRegistry.createRegistry(1099);
                            IEffectenbeurs mockeffectenbeurs = new MockEffectenBeurs();
                            registry.rebind("Mockeffectenbeurs", mockeffectenbeurs);
                        } catch (RemoteException ex) {

                        }
                        
                        generateKoersen();
                                            
                    }
                });
            }
        };

        koersenTimer.scheduleAtFixedRate(task, 0, 12000);
    }

    public IFonds[] generateKoersen() {
        fondsen = new IFonds[5];
        fondsen[0] = new Fonds("Microsoft", generateKoers());
        fondsen[1] = new Fonds("Apple", generateKoers());
        fondsen[2] = new Fonds("Google", generateKoers());
        fondsen[3] = new Fonds("Nokia", generateKoers());
        fondsen[4] = new Fonds("Yahoo", generateKoers());
        return fondsen;
    }

    @Override
    public IFonds[] getKoersen() throws RemoteException {

        return this.fondsen;
    }

    public double generateKoers() {
        Random r = new Random();
        double koers = r.nextInt(101) + r.nextDouble();
        koers = Math.round(koers * 10);
        koers = koers / 10;
        if (koers < 10) {
            generateKoers();
        }
        return koers;
    }
}
