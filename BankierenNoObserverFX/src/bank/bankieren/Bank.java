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
    private static final long serialVersionUID = -8728841131739353765L;
    private Map<String, IRekeningTbvBank> accounts;
    private Collection<IKlant> clients;
    private int nieuwReknr;
    private String name;
    private String prefix;
    private Lock bankLock = new ReentrantLock();
    private ICentrale centrale;
    private BasicPublisher bp = new BasicPublisher(new String[]
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
        catch (NotBoundException | AccessException ex)
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
            //accounts.put(prefix + String.valueOf(nieuwReknr), account);

            try
            {
                centrale.openRekening(prefix + String.valueOf(nieuwReknr), account);
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
        IRekening rek = null;
        try
        {
            rek = centrale.getRekening(nr);
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rek;
    }

    @Override
    public boolean maakOver(String source, String destination, Money money) throws NumberDoesntExistException
    {
        bankLock.lock();
        boolean gelukt = false;
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

            try
            {
                gelukt = centrale.maakOver(source, destination, money);
            }
            catch (RemoteException ex)
            {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        finally
        {
            bankLock.unlock();
        }
        
        return gelukt;
    }
    
    @Override
    public void informSession(Money saldo)
    {
        System.out.println("Nieuwe saldo van source: " + saldo);
        bp.inform(this, "Saldo", null, saldo);
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
}
