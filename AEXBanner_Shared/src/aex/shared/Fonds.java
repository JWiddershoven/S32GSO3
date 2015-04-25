/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aex.shared;

import aex.shared.IFonds;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Jelle
 */
public class Fonds extends UnicastRemoteObject implements IFonds, Serializable {

    private String naam;
    private double koers;
    
    public Fonds(String naam, double koers) throws RemoteException
    {
        this.naam = naam;
        this.koers = koers;
    }
    @Override
    public String getNaam() {
        return this.naam;
    }

    @Override
    public double getKoers() {
        return this.koers;
    }
    
}
