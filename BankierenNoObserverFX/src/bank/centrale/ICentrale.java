/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.IBank;
import bank.bankieren.Money;
import fontys.observer.RemotePublisher;
import fontys.util.NumberDoesntExistException;
import java.io.Serializable;
import java.rmi.Remote;

/**
 *
 * @author Jelle
 */
public interface ICentrale extends Remote, Serializable, RemotePublisher
{
    public void addBank (IBank bank);
    
    public void removeBank (String bankName);
    
    public boolean maakOver (IBank bank, int herkomst, int bestemming, Money bedrag) throws NumberDoesntExistException;
}
