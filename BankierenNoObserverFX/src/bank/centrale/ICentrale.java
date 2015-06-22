/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.IRekeningTbvBank;
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
    /**
     * Voegt een bank toe aan de centrale
     * @param bank Het bank object dat bij de balieServer is aangemaakt
     * @throws RemoteException 
     */
    public void addBank (IBank bank) throws RemoteException;
    
    /**
     * Verwijdert een bank van de centrale
     * @param bankName de naam van de bank, mag niet null of leeg zijn
     * @throws RemoteException 
     */
    public void removeBank (String bankName)throws RemoteException;
    
    /**
     * Verwerkt de transactie tussen de twee rekeningen
     * @param herkomst het rekeningnummer van de source, mag niet null of leeg zijn
     * @param bestemming het rekeningnummer van de tegenpartij, mag niet null of leeg zijn
     * @param bedrag het bedrag dat wordt overgemaakt, mag niet null, negatief of leeg zijn
     * @return boolean indien gelukt, anders false
     * @throws NumberDoesntExistException
     * @throws RemoteException 
     */
    public boolean maakOver (String herkomst, String bestemming, Money bedrag) throws NumberDoesntExistException, RemoteException;
    
    /**
     * Voegt een nieuwe rekening toe aan de centrale
     * @param rekNummer het nieuwe rekeningnummer van de klant
     * @param account de rekening
     * @throws RemoteException 
     */
    public void openRekening (String rekNummer, IRekeningTbvBank account) throws RemoteException;
    
    /**
     * @param nr het rekeningnummer dat wordt opgevraagd, mag niet null of leeg zijn
     * @return het rekening object dat hoort bij het rekeningnummer
     * @throws RemoteException 
     */
    public IRekening getRekening(String nr) throws RemoteException;
    
    /**
     * @param prefix de prefix van de bank
     * @return het bank object dat hoort bij de prefix
     * @throws RemoteException 
     */
    public IBank getBank(String prefix) throws RemoteException;
}

