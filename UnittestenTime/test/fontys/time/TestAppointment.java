/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
/**
 *
 * @author Jordy
 */
public class TestAppointment {
    
    @Test public void testAppointment()
    {
        /**
     * Er wordt een nieuwe appointment gecreëerd met een subject en timespan.
     * Deze appointment krijgt een lege lijst met deelnemers.
     * @param subject subject mag een lege string zijn.
     * @param timeSpan
     */
        try {
            String subject = "Test";
            TimeSpan ts = new TimeSpan(new Time(2015, 3, 22, 11, 35), new Time(2015, 3, 22, 11, 50));
            Appointment a = new Appointment(subject, ts);
            
            String subject2 = "";
            Appointment a2 = new Appointment(subject2, ts);
            
        } catch (IllegalArgumentException exc) {
            fail("Subject mag een lege string bevatten");
        }
    }
    
}
