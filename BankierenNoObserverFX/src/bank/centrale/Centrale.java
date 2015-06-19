/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.IRekeningTbvBank;
import bank.bankieren.Money;
import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import bank.server.BalieServer;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.util.NumberDoesntExistException;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Jelle
 */
public class Centrale extends UnicastRemoteObject implements ICentrale
{

    private ArrayList<IBank> banks;
    private BasicPublisher bp = new BasicPublisher(new String[]
    {
        "ExternOvermaken"
    });

    public Centrale() throws RemoteException
    {
        banks = new ArrayList<>();     
    }

    @Override
    public void addBank(IBank bank)
    {
        for (IBank b : banks)
        {
            if (!b.getName().equals(bank.getName()))
            {
                banks.add(bank);
            }
        }
    }

    @Override
    public void removeBank(String bankName)
    {
        for (IBank b : banks)
        {
            if (b.getName().equals(bankName))
            {
                banks.remove(b);
            }
        }
    }

    @Override
    public boolean maakOver(String herkomst, String bestemming, Money bedrag) throws NumberDoesntExistException
    {
            String prefixSource = herkomst.substring(0, 3);
            String prefixDestination = bestemming.substring(0, 3);
            
            for (IBank bank : banks)
            {
                if (bank.getPrefix().equals(prefixDestination))
                {
                    boolean result = bank.maakOverExtern(herkomst, bestemming, bedrag);
                    if (result)
                    {
                        for (IBank b : banks)
                        {
                            if (b.getPrefix().equals(prefixSource))
                            {
                                bp.inform(this, "ExternOvermaken", null, true);
                            }
                        }
                    }
                }
            }
        
        return false;
    }
    
    public void openRekening(String naam, String city, String bankNaam)
    {
        for (IBank bank : banks)
        {
            if (bank.getName().equals(bankNaam))
            {
                bank.voegRekeningToe(naam, city);
            }
        }
    }
    @Override
    public void addListener(RemotePropertyListener rl, String string) throws RemoteException
    {
        IBank bank = (IBank) rl;
        banks.add(bank);
        bp.addListener(rl, string);
    }

    @Override
    public void removeListener(RemotePropertyListener rl, String string) throws RemoteException
    {
        bp.removeListener(rl, string);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) throws RemoteException
    {
        
    }

}
