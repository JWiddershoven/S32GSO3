/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import java.rmi.*;
/**
 *
 * @author Jelle
 */
public interface IEffectenbeurs extends Remote {
    
    /**
     * @return De fondsen.
     * @throws java.rmi.RemoteException
     */
    public IFonds[] getKoersen() throws RemoteException;
    
}
