/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bank.bankieren.Bank;
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

    @Before
    public void setUp() {
        Bank bank = new Bank("ING");
        
    }
    
    @Test
    public void testIsGeldig() {
        /**
	 * @returns true als de laatste aanroep van getRekening of maakOver voor deze
	 *          sessie minder dan GELDIGHEIDSDUUR geleden is
	 *          en er geen communicatiestoornis in de tussentijd is opgetreden, 
	 *          anders false
	 */
        
    }
}
