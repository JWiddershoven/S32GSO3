/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jordy
 */
public class BalieTest {

    static IBank bank;
    static IBalie balie;
    static String accountname;

    public BalieTest() {

    }

    @BeforeClass
    public static void setUpClass() throws RemoteException {
        bank = new Bank("ING");
        balie = new Balie(bank);
        accountname = balie.openRekening("Jordy", "Valkenswaard", "Password");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws RemoteException {

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testOpenRekening() {

        /**
         * creatie van een nieuwe bankrekening; het gegenereerde
         * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
         * heeft een saldo van 0 euro
         *
         * @param naam van de eigenaar van de nieuwe bankrekening
         * @param plaats de woonplaats van de eigenaar van de nieuwe
         * bankrekening
         * @param wachtwoord van het account waarmee er toegang kan worden
         * verkregen tot de nieuwe bankrekening
         * @return null zodra naam of plaats een lege string of wachtwoord
         * minder dan vier of meer dan acht karakters lang is en anders de
         * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
         * nieuwe bankrekening kan worden verkregen
         */
        try {
            assertNotNull(balie.openRekening("Jordy", "Valkenswaard", "Password"));
            assertNull("Naam is een lege String.", balie.openRekening("", "Valkenswaard ", "Password"));
            assertNull("Plaats is een lege String.", balie.openRekening("Jordy", "", "Password"));
            assertNull("Wachtwoord is een lege String.", balie.openRekening("Jordy", "Valkenswaard", ""));
            assertNull("Wachtwoord minder dan vier karakters lang.", balie.openRekening("Jordy", "Valkenswaard", "Pas"));
            assertNull("Wachtwoord meer dan acht karakters lang.", balie.openRekening("Jordy", "Valkenswaard", "123456789"));

        } catch (RemoteException ex) {
            Logger.getLogger(BalieTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testLogIn() {

        /**
         * er wordt een sessie opgestart voor het login-account met de naam
         * accountnaam mits het wachtwoord correct is
         *
         * @param accountnaam
         * @param wachtwoord
         * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
         * tot de bankrekening die hoort bij het betreffende login- account mits
         * accountnaam en wachtwoord matchen, anders null
         */
        
        try {
            assertNotNull("Inloggen met juiste gegevens.", balie.logIn(accountname, "Password"));
            assertNull("Inloggen met onjuist wachtwoord.", balie.logIn(accountname, "password123"));
            assertNull("Inloggen met onjuist accountname.", balie.logIn("Jordy", "Password"));
            assertNull("Inloggen met onjuist accountname.", balie.logIn("", "Password"));
            assertNull("Inloggen met onjuist wachtwoord.", balie.logIn(accountname, ""));
        } catch (RemoteException ex) {
            Logger.getLogger(BalieTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
