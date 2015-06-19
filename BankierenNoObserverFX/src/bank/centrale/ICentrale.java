/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.IBank;
import bank.bankieren.Money;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import fontys.util.NumberDoesntExistException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Jelle
 */
public interface ICentrale extends Remote, RemotePublisher, RemotePropertyListener
{
    public void addBank (IBank bank) throws RemoteException;
    
    public void removeBank (String bankName)throws RemoteException;
    
    public boolean maakOver (String herkomst, String bestemming, Money bedrag) throws NumberDoesntExistException, RemoteException;
    
    public void openRekening (String naam, String city, String prefix) throws RemoteException;
}
