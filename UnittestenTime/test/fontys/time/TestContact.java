/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jelle
 */
public class TestContact {
    
@Test 
public void testContact()
{
     /**
     * Er wordt een nieuw contact gecreëerd met een naam.
     * Dit contact krijgt een nieuwe, lege agenda.
     */
    // @param name naam mag geen lege string zijn.
    try {
        Contact con = new Contact("Jelle");
        assertEquals("Jelle", con.getName());
    } catch (IllegalArgumentException exc){
        
    }
    
    try {
        Contact con = new Contact("");
        fail("Naam mag geen lege string zijn.");
    } catch (IllegalArgumentException exc) {
        
    }   
}

@Test
public void testAddAppointment() {
    
    Appointment a = new Appointment(); //to-do: geef subject en TimeSpan mee als parameter
    Contact con = new Contact("Jelle");
    try { 
        boolean result = con.addAppointment(a);
        assertEquals(true, result);
    } catch (IllegalArgumentException exc) {
        
    }
    
    try {
        boolean result = con.addAppointment(a);
        assertEquals(false, result);
    } catch (IllegalArgumentException exc) {
        
    }
}

@Test
public void testRemoveAppointment() { //To-do: Checken of de appointment daadwerkelijk uit de lijst is verdwenen. Iterator to list?
    try {
        Appointment a = new Appointment();
        Contact con = new Contact("Jelle");
        con.removeAppointment(a);
    } catch (IllegalArgumentException exc) {
        
    }
}

}
