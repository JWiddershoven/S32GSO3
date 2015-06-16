/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.IBank;
import bank.bankieren.IRekeningTbvBank;
import bank.bankieren.Money;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.util.NumberDoesntExistException;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Jelle
 */
public class Centrale implements ICentrale
{

    private transient Lock centraleLock = new ReentrantLock();
    private ArrayList<IBank> banks;
    private transient BasicPublisher bp = new BasicPublisher(new String[]
    {
        "RekeningHerkomst",
        "RekeningBestemming",
        "Bedrag"
    });

    public Centrale()
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
    public boolean maakOver(IBank bank, String herkomst, String bestemming, Money bedrag) throws NumberDoesntExistException
    {
        centraleLock.lock();
        try
        {
            if (herkomst.equals(bestemming))
            {
                throw new RuntimeException(
                        "cannot transfer money to your own account");
            }
            if (!bedrag.isPositive())
            {
                throw new RuntimeException("money must be positive");
            }

            IRekeningTbvBank source_account = (IRekeningTbvBank) bank.getRekening(herkomst);
            if (source_account == null)
            {
                throw new NumberDoesntExistException("account " + herkomst
                        + " unknown at " + bank.getName());
            }

            Money negative = Money.difference(new Money(0, bedrag.getCurrency()),
                    bedrag);
            boolean success = source_account.muteer(negative);
            if (!success)
            {
                return false;
            }

            IRekeningTbvBank dest_account = (IRekeningTbvBank) bank.getRekening(bestemming);
            if (dest_account == null)
            {
                throw new NumberDoesntExistException("account " + bestemming
                        + " unknown at " + bank.getName());
            }
            success = dest_account.muteer(bedrag);

            if (success)
            {
                bp.inform(this, "Saldo", null, source_account.getSaldo().getValue());
            }

            if (!success) // rollback
            {
                source_account.muteer(bedrag);
            }

            return success;

        }
        finally
        {
            centraleLock.unlock();
        }
    }

    @Override
    public void addListener(RemotePropertyListener rl, String string) throws RemoteException
    {
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
