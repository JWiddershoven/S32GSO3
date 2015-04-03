/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jelle
 */
public class TestAppointment {
    
     /**
     * Er wordt een nieuwe appointment gecreëerd met een subject en timeSpan.
     */
    @Test
    public void testAppointment()
    {
        // @param subject het onderwerp van een afspraak, mag een lege string zijn.
        try {
            Appointment app = new Appointment("", new TimeSpan(new Time(2015, 3, 23, 10, 00), new Time(2015, 3, 23, 11, 00)));
            assertEquals("", app.getSubject());
        } catch (IllegalArgumentException exc) {
            
        }
        
        // @param timeSpan de timeSpan van een afspraak.
        try {
            TimeSpan ts = new TimeSpan(new Time(2015, 3, 23, 10, 00), new Time(2015, 3, 23, 11, 00));
            Appointment app = new Appointment("meeting", ts);
            assertEquals(ts, app.getTimeSpan());
        } catch (IllegalArgumentException exc) {
            
        }
    }
    
    @Test
    public void testAddContact()
    {
        Contact con = new Contact("Jelle");
        Appointment app = new Appointment("overleg", new TimeSpan(new Time(2015, 3, 23, 17, 00), new Time(2015, 3, 23, 18, 00)));
        
        try {
            boolean result = app.addContact(con);
            assertEquals(true, result);
        } catch (IllegalArgumentException exc) {
            
        }
                 
    }
    
    @Test 
    public void testRemoveContact()
    {
        try {
            Appointment a = new Appointment("vergadering2", new TimeSpan(new Time(2015, 3, 23, 15, 00), new Time(2015, 3, 23, 16, 00)));
            Contact con = new Contact("Jelle");
            a.removeContact(con);
        } catch (IllegalArgumentException exc) {
            
        }
    }
}
