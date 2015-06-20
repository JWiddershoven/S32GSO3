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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jelle
 */
public class Centrale extends UnicastRemoteObject implements ICentrale
{

    private ArrayList<IBank> banks;
    private transient Lock bankLock = new ReentrantLock();
    private BasicPublisher bp = new BasicPublisher(new String[]
    {
        "ExternOvermaken"
    });

    private Map<String, IRekeningTbvBank> accounts;

    public Centrale() throws RemoteException
    {
        this.banks = new ArrayList<>();
        this.accounts = new HashMap<>();
    }

    @Override
    public void addBank(String bankName)
    {
        try
        {
            banks.add(new Bank(bankName));
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(Centrale.class.getName()).log(Level.SEVERE, null, ex);
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
    public IRekening getRekening(String nr)
    {
        return accounts.get(nr);
    }

    @Override
    public IBank getBank(String prefix)
    {
        IBank bank = null;
        for (IBank b : banks)
        {
            if (b.getPrefix().equals(prefix))
            {
                bank = b;
            }
        }

        return bank;
    }

    @Override
    public boolean maakOver(String source, String destination, Money money) throws NumberDoesntExistException
    {
        bankLock.lock();
        try
        {
            String prefixSource = source.substring(0, 3);
            String prefixDestination = destination.substring(0, 3);

            IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);
            if (source_account == null)
            {
                throw new NumberDoesntExistException("account " + source
                        + " unknown at " + prefixSource);
            }

            IBank bankSource = getBank(prefixSource);
            IBank bankDestination = getBank(prefixDestination);

            boolean success = false;

            Money negative = Money.difference(new Money(0, money.getCurrency()), money);

            System.out.println("Source: " + negative);
            System.out.println("Parameter: " + money);

            success = source_account.muteer(negative);
            if (!success)
            {
                return false;
            }
            
            IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(destination);
            if (dest_account == null)
            {
                throw new NumberDoesntExistException("account " + destination
                        + " unknown at " + prefixDestination);
            }
            success = dest_account.muteer(money);

            
            if (success)
            {
                bankSource.informSession(source_account.getSaldo());
            }

            if (!success) // rollback
            {
                source_account.muteer(money);
            }

            return success;
        }

        finally
        {
            bankLock.unlock();
        }
    }

    @Override
    public void openRekening(String rekNummer, IRekeningTbvBank account)
    {
        accounts.put(rekNummer, account);
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
