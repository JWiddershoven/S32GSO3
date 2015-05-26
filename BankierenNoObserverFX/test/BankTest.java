/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import bank.bankieren.*;
import fontys.util.NumberDoesntExistException;

/**
 *
 * @author Jordy
 */
public class BankTest {

    IBank bank;
    IRekeningTbvBank rekening;
    IKlant klant;
    Money money;

    @Before
    public void setUp() {
        bank = new Bank("ABN Amro");
        money = new Money(100000, "€");
        rekening = new Rekening(100000000, new Klant("Jordy", "Valkenswaard"), money);
    }

    public BankTest() {
    }

    @Test
    public void testOpenRekening() {
        /**
         * creatie van een nieuwe bankrekening met een identificerend
         * rekeningnummer; alleen als de klant, geidentificeerd door naam en
         * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
         *
         * @param naam van de eigenaar van de nieuwe bankrekening
         * @param plaats de woonplaats van de eigenaar van de nieuwe
         * bankrekening
         * @return -1 zodra naam of plaats een lege string en anders het nummer
         * van de gecreeerde bankrekening
         */
        try {
            assertEquals(100000000, bank.openRekening("Jordy", "Valkenswaard"));
            assertEquals(100000001, bank.openRekening("Kees", "Amsterdam"));
        } catch (IllegalArgumentException exc) {
            fail("Bankrekening nummer komt niet overeen.");
        }
    }

    @Test
    public void testOpenRekeningMetLegeString() {
        try {
            assertEquals(-1, bank.openRekening("", ""));
            assertEquals(-1, bank.openRekening("", "Valkenswaard"));
            assertEquals(-1, bank.openRekening("Jordy", ""));
        } catch (IllegalArgumentException exc) {
            fail("Lege string is niet toegestaan als parameter.");
        }

    }

    @Test
    public void testGetRekeningMetBestaandNummer() {
        /**
         * @param nr
         * @return de bankrekening met nummer nr mits bij deze bank bekend,
         * anders null
         */
        try {
            bank.openRekening("Jordy", "Valkenswaard");
            bank.openRekening("Kees", "Amsterdam");
            assertNotNull(bank.getRekening(100000000));
        } catch (IllegalArgumentException exc) {
            fail("Er bestaat wel een bankrekening met gegeven rekeningnummer.");
        }

        try {
            assertEquals(rekening, bank.getRekening(100000000));
        } catch (IllegalArgumentException exc) {
            fail("Bankrekening komt niet overeen.");
        }

    }

    @Test
    public void testGetRekeningMetNummerDatNietBestaat() {
        try {
            bank.openRekening("Jordy", "Valkenswaard");
            bank.openRekening("Kees", "Amsterdam");
            assertNull("Nummer bestaat niet.", bank.getRekening(102300000));
        } catch (IllegalArgumentException exc) {
            fail("Gegeven rekeningnummer bestaat niet.");
        }
    }

    @Test
    public void getName() {
        /**
         * @return de naam van deze bank
         */
        try {
            assertEquals("ABN Amro", bank.getName());
        } catch (IllegalArgumentException exc) {
            fail("Ongeldige banknaam.");
        }

        try {
            assertNotSame("abn amro", bank.getName());
        } catch (IllegalArgumentException exc) {
            fail("Ongeldige banknaam.");
        }

    }

    @Test
    public void testMaakOver() {
        /**
         * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
         * de bankrekening met nummer bestemming, mits het afschrijven van het
         * bedrag van de rekening met nr bron niet lager wordt dan de
         * kredietlimiet van deze rekening
         *
         * @param bron
         * @param bestemming ongelijk aan bron
         * @param bedrag is groter dan 0
         * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
         * @throws NumberDoesntExistException als een van de twee
         * bankrekeningnummers onbekend is
         */
        try {
            bank.openRekening("Jordy", "Valkenswaard");
            bank.openRekening("Kees", "Amsterdam");
            assertTrue("Geld overmaken met geldige parameters", bank.maakOver(100000000, 100000001, new Money(1000, "€")));
        } catch (IllegalArgumentException | NumberDoesntExistException exc) {
            fail(exc.getMessage());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testMaakOverNaarZelfdeRekening() throws NumberDoesntExistException {
        bank.openRekening("Jordy", "Valkenswaard");
        bank.openRekening("Kees", "Amsterdam");
        bank.maakOver(100000000, 100000000, new Money(1000, "€"));
    }

    @Test(expected = RuntimeException.class)
    public void testMaakOverMetNegatiefBedrag() throws NumberDoesntExistException {
        bank.openRekening("Jordy", "Valkenswaard");
        bank.openRekening("Kees", "Amsterdam");
        bank.maakOver(100000000, 100000001, new Money(-1000, "€"));
    }
}
