package bank.internettoegang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.beans.PropertyChangeEvent;

public class Bankiersessie extends UnicastRemoteObject implements IBankiersessie
{

    private static final long serialVersionUID = 1L;
    private long laatsteAanroep;
    private String reknr;
    private IBank bank;
    private BasicPublisher bp = new BasicPublisher(new String[]
    {
        "Saldo"
    });

    public Bankiersessie(String reknr, IBank bank) throws RemoteException
    {
        laatsteAanroep = System.currentTimeMillis();
        this.reknr = reknr;
        this.bank = bank;
        bank.addListener(this, "Saldo");
    }

    @Override
    public boolean isGeldig()
    {
        return System.currentTimeMillis() - laatsteAanroep < GELDIGHEIDSDUUR;
    }

    @Override
    public boolean maakOver(String bestemming, Money bedrag)
            throws NumberDoesntExistException, InvalidSessionException,
            RemoteException
    {

        updateLaatsteAanroep();

        if (reknr.equals(bestemming))
        {
            throw new RuntimeException(
                    "source and destination must be different");
        }
        if (!bedrag.isPositive())
        {
            throw new RuntimeException("amount must be positive");
        }

        return bank.maakOver(reknr, bestemming, bedrag);
    }

    private void updateLaatsteAanroep() throws InvalidSessionException
    {
        if (!isGeldig())
        {
            throw new InvalidSessionException("session has been expired");
        }

        laatsteAanroep = System.currentTimeMillis();
    }

    @Override
    public IRekening getRekening() throws InvalidSessionException,
            RemoteException
    {

        updateLaatsteAanroep();

        return bank.getRekening(reknr);
    }

    @Override
    public void logUit() throws RemoteException
    {
        UnicastRemoteObject.unexportObject(this, true);
        removeListener(this, "Saldo");
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) throws RemoteException
    {
        System.out.println("Saldo bij bankiersessie: " + pce.getNewValue());
        bp.inform(this, "Saldo", null, pce.getNewValue());
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

}
