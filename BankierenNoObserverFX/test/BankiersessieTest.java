/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bank.bankieren.Bank;
import bank.bankieren.IKlant;
import bank.bankieren.IRekening;
import bank.bankieren.Klant;
import bank.bankieren.Money;
import bank.bankieren.Rekening;
import bank.internettoegang.Bankiersessie;
import bank.internettoegang.IBankiersessie;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Jordy
 */
public class BankiersessieTest {

    public BankiersessieTest() {
    }
    Bank bank;
    IBankiersessie sessie;
    Money bedrag;
    IRekening rekening;
    IKlant klant;

    @Before
    public void setUp() throws RemoteException {
        bank = new Bank("ING");
        sessie = new Bankiersessie(100000000, bank);
        bedrag = new Money(500000, "€");
        klant = new Klant("Jordy", "Valkenswaard");
        bank.openRekening("Jordy", "Valkenswaard");
        rekening = new Rekening(100000000, klant, bedrag);
        

    }

    @Test
    public void testMaakOver() {
        /**
         * er wordt bedrag overgemaakt van de bankrekening met het nummer bron
         * naar de bankrekening met nummer bestemming
         *
         * @param bron
         * @param bestemming is ongelijk aan rekeningnummer van deze
         * bankiersessie
         * @param bedrag is groter dan 0
         * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
         * @throws NumberDoesntExistException als bestemming onbekend is
         * @throws InvalidSessionException als sessie niet meer geldig is
         */

        //Geld overmaken naar zelfde rekening
        try {
            try {
                sessie.maakOver(100000000, bedrag);
                fail("Bron en bestemming mogen niet gelijk zijn.");
            } catch (NumberDoesntExistException | InvalidSessionException | RemoteException ex) {
                Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RuntimeException exc) {
            Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, exc);
        }

        //Geld overmaken met een negatief bedrag
        try {
            try {
                sessie.maakOver(100000001, new Money(-50000, "€"));
                fail("Bedrag mag niet negatief zijn.");
            } catch (NumberDoesntExistException | InvalidSessionException | RemoteException ex) {
                Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RuntimeException exc) {
            Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, exc);
        }

        try {
            //Geld overmaken naar onbekende bestemmin
            assertFalse(sessie.maakOver(100000999, new Money(50000, "€")));
        } catch (NumberDoesntExistException | InvalidSessionException | RemoteException ex) {
            Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testGetRekening() {

        /**
         * @return de rekeninggegevens die horen bij deze sessie
         * @throws InvalidSessionException als de sessieId niet geldig of
         * verlopen is
         * @throws RemoteException
         */
        try {
            assertEquals(rekening, sessie.getRekening());
        } catch (InvalidSessionException | RemoteException ex) {
            fail("Rekening nummer komt niet overeen");
        }

    }

    @Test
    public void testIsGeldig() {
        /**
         * @returns true als de laatste aanroep van getRekening of maakOver voor
         * deze sessie minder dan GELDIGHEIDSDUUR geleden is en er geen
         * communicatiestoornis in de tussentijd is opgetreden, anders false
         */

    }
}
