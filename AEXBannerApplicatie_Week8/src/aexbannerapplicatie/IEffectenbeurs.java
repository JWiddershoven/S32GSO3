/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

import java.rmi.*;
import remote.Publisher;
/**
 *
 * @author Jelle
 */
public interface IEffectenbeurs extends Publisher {
    
    /**
     * @return De fondsen.
     * @throws java.rmi.RemoteException
     */
    public IFonds[] getKoersen() throws RemoteException;
    
}
