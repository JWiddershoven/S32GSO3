/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Jelle
 */

public class Appointment {
    private String subject;
    private ITimeSpan ts;
    private ArrayList<Contact> deelnemers;
    
    
    /**
     * Er wordt een nieuwe appointment gecreëerd met een subject en timespan.
     * Deze appointment krijgt een lege lijst met deelnemers.
     * @param subject subject mag een lege string zijn.
     * @param timeSpan
     */
    public Appointment(String subject, ITimeSpan timeSpan)
    {
        this.subject = subject;
        this.ts = timeSpan;
        this.deelnemers = new ArrayList<>();
    }
    
    /** @return de subject van deze appointment */
    public String getSubject()
    {
        return subject;
    }
    
    /** @return de timespan van deze appointment */
    public ITimeSpan getTimeSpan()
    {
        return ts;
    }
    
    /**
     * @return een Iterator met alle deelnemers van de appointment.
     */
    public Iterator<Contact> invitees()
    {
        Iterator itr = deelnemers.iterator();
        return itr;
    }
    
    /**
     * Er wordt een contact toegevoegd aan de appointment.
     * @param c Contact moet een naam bevatten.
     * @return true indien de Contact is toegevoegd aan de appointment, anders false.
     */
    public boolean addContact(Contact c)
    {
        if (deelnemers.contains(c))
        {
            return false;
        }
        else
        {
            deelnemers.add(c);
            return true;
        }
    }
    
    /**
     * Er wordt een contact verwijderd van de appointment indien deze voorkomt.
     * @param c Contact moet voorkomen in deelnemers van het appointment.
     */
    public void removeContact(Contact c)
    {
        if (deelnemers.contains(c))
        {
            deelnemers.remove(c);
        }
    }
    
    
}
