package bank.bankieren;

import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import fontys.util.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
    private BasicPublisher bp = new BasicPublisher(new String[]
    {
        "Saldo"
    });

    public Bank(String name)
    {
        this.accounts = new HashMap<>();
        this.clients = new ArrayList<>();
        this.nieuwReknr = 100000000;
        this.prefix = name.substring(0, 3);
        this.name = name;
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

            System.out.println("Prefix: " + prefix);
            System.out.println("Rekeningnummer: " + nieuwReknr);
            nieuwReknr++;
            return prefix + String.valueOf(nieuwReknr - 1);
        } finally
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

            Money negative = Money.difference(new Money(0, money.getCurrency()),
                    money);
            boolean success = source_account.muteer(negative);
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

        } finally
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
