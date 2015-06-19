package bank.bankieren;

import bank.centrale.Centrale;
import bank.centrale.ICentrale;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import fontys.util.*;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank implements IBank
{

    /**
     *
     */
    private transient static final long serialVersionUID = -8728841131739353765L;
    private Map<String, IRekeningTbvBank> accounts;
    private Collection<IKlant> clients;
    private int nieuwReknr;
    private String name;
    private String prefix;
    private transient Lock bankLock = new ReentrantLock();
    private transient ICentrale centrale;
    private transient BasicPublisher bp = new BasicPublisher(new String[]
    {
        "Saldo"
    });
    private boolean externOvermaken = false;

    public Bank(String name) throws RemoteException
    {
        this.accounts = new HashMap<>();
        this.clients = new ArrayList<>();
        this.nieuwReknr = 100000000;
        this.prefix = name.substring(0, 3);
        this.name = name;
        Registry reg = LocateRegistry.getRegistry(1098);
        try
        {
            this.centrale = (ICentrale) reg.lookup("centrale");

        }
        catch (NotBoundException ex)
        {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (AccessException ex)
        {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            centrale.addListener(this, "ExternOvermaken");
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String openRekening(String name, String city)
    {
        bankLock.lock();
        try
        {
            if (name.equals("") || city.equals(""))
            {
                return "-1";
            }

            IKlant klant = getKlant(name, city);
            IRekeningTbvBank account = new Rekening(prefix + String.valueOf(nieuwReknr), klant, Money.EURO);
            accounts.put(prefix + String.valueOf(nieuwReknr), account);

            try
            {
                centrale.openRekening(name, city, this.name);
            }
            catch (RemoteException ex)
            {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Rekening: " + prefix + nieuwReknr);
            nieuwReknr++;
            return prefix + String.valueOf(nieuwReknr - 1);
        }
        finally
        {
            bankLock.unlock();
        }
    }

    @Override
    public String voegRekeningToe(String naam, String city)
    {
        if (name.equals("") || city.equals(""))
        {
            return "-1";
        }

        IKlant klant = getKlant(name, city);
        IRekeningTbvBank account = new Rekening(prefix + String.valueOf(nieuwReknr), klant, Money.EURO);
        accounts.put(prefix + String.valueOf(nieuwReknr), account);

        System.out.println("Rekening: " + prefix + nieuwReknr);
        nieuwReknr++;
        return prefix + String.valueOf(nieuwReknr - 1);

    }

    private IKlant getKlant(String name, String city)
    {
        for (IKlant k : clients)
        {
            if (k.getNaam().equals(name) && k.getPlaats().equals(city))
            {
                return k;
            }
        }
        IKlant klant = new Klant(name, city);
        clients.add(klant);
        return klant;
    }

    @Override
    public IRekening getRekening(String nr)
    {
        return accounts.get(nr);
    }

    @Override
    public boolean maakOver(String source, String destination, Money money) throws NumberDoesntExistException
    {
        bankLock.lock();
        try
        {
            if (source.equals(destination))
            {
                throw new RuntimeException(
                        "cannot transfer money to your own account");
            }
            if (!money.isPositive())
            {
                throw new RuntimeException("money must be positive");
            }

            IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);
            if (source_account == null)
            {
                throw new NumberDoesntExistException("account " + source
                        + " unknown at " + name);
            }

            String prefixSource = prefix;
            String prefixDestination = destination.substring(0, 3);

            boolean success = false;

            Money negative = Money.difference(new Money(0, money.getCurrency()), money);

            if (prefixSource.equals(prefixDestination))
            {
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
                            + " unknown at " + name);
                }
                success = dest_account.muteer(money);

                if (success)
                {
                    bp.inform(this, "Saldo", null, source_account.getSaldo().getValue());
                }

                if (!success) // rollback
                {
                    source_account.muteer(money);
                }

                return success;
            }
            else
            {
                try
                {
                    success = source_account.muteer(negative);

                    if (success)
                    {
                        centrale.maakOver(source, destination, money);
                        System.out.println(externOvermaken);
                    }
                }
                catch (RemoteException ex)
                {
                    Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (externOvermaken)
                {
                    success = true;
                }

                return success;
            }

        }
        finally
        {
            bankLock.unlock();
        }

    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getPrefix()
    {
        return this.prefix;
    }

    @Override
    public void addListener(RemotePropertyListener rl, String property) throws RemoteException
    {
        bp.addListener(rl, property);
    }

    @Override
    public void removeListener(RemotePropertyListener rl, String property) throws RemoteException
    {
        bp.removeListener(rl, property);
    }

    @Override
    public boolean maakOverExtern(String herkomst, String bestemming, Money bedrag) throws NumberDoesntExistException
    {
        boolean success = false;
        if (!bedrag.isPositive())
        {
            throw new RuntimeException("money must be positive");
        }

        IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(bestemming);
        if (dest_account == null)
        {
            throw new NumberDoesntExistException("account " + bestemming
                    + " unknown at " + name);
        }

        success = dest_account.muteer(bedrag);

        if (success)
        {
            bp.inform(this, "Saldo", null, dest_account.getSaldo().getValue());
        }

        if (!success) // rollback
        {
            try
            {
                centrale.maakOver(bestemming, herkomst, bedrag);
            }
            catch (RemoteException ex)
            {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return success;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) throws RemoteException
    {
        this.externOvermaken = (Boolean) pce.getNewValue();
    }

}
