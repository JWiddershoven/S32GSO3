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
import org.junit.BeforeClass;

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

    @BeforeClass
    public static void setUpClass() throws RemoteException {
        
    }

    @Before
    public void setUp() throws RemoteException {
        bank = new Bank("ING");
        bedrag = new Money(500000, "€");
        klant = new Klant("Jordy", "Valkenswaard");
        bank.openRekening("Jordy", "Valkenswaard");
        bank.openRekening("Jelle", "Dennenlaan");
        sessie = new Bankiersessie("ING100000000", bank);
        rekening = new Rekening("ING100000000", klant, bedrag);
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
        
        //Geld overmaken naar bekende bestemming
        try {
            assertTrue("Juiste transactie", sessie.maakOver("ING100000001", new Money(1000, "€")));
        } catch (NumberDoesntExistException | InvalidSessionException | RemoteException ex) {
            Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Geld overmaken naar onbekende bestemming
        try {
            assertFalse(sessie.maakOver("ING100000999", new Money(50000, "€")));
        } catch (NumberDoesntExistException | InvalidSessionException | RemoteException ex) {
            Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Geld overmaken naar zelfde rekening
        try {
            try {
                sessie.maakOver("ING100000000", new Money(1000, "€"));
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
                sessie.maakOver("ING100000001", new Money(-50000, "€"));
                fail("Bedrag mag niet negatief zijn.");
            } catch (NumberDoesntExistException | InvalidSessionException | RemoteException ex) {
                Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RuntimeException exc) {
            Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, exc);
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
        //Juiste rekening opvragen
        try {
            assertEquals("Rekening gegevens zijn hetzelfde.", rekening, sessie.getRekening());
        } catch (InvalidSessionException | RemoteException ex) {
            fail("Rekening nummer komt niet overeen");
        }
    }

    @Test
    public void testIsGeldig() {
        try {
            /**
             * @returns true als de laatste aanroep van getRekening of maakOver voor
             * deze sessie minder dan GELDIGHEIDSDUUR geleden is en er geen
             * communicatiestoornis in de tussentijd is opgetreden, anders false
             */
            assertTrue(sessie.isGeldig());
        } catch (RemoteException ex) {
            fail("Sessie is niet meer geldig.");
        }
    }
}
