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
    private ITimeSpan timeSpan;
    private ArrayList<Contact> invitees;
    
    /**
     * Er wordt een nieuwe appointment gecreëerd met een subject en timeSpan.
     * @param subject het onderwerp van een afspraak, mag een lege string zijn.
     * @param timeSpan de timeSpan van een afspraak.
     */
    public Appointment(String subject, ITimeSpan timeSpan)
    {
        this.subject = subject;
        this.timeSpan = timeSpan;
        invitees = new ArrayList<>();
    }
    /**
     * @return het subject van een appointment.
     */
    public String getSubject() 
    {
        return this.subject;
    }
    
    /**
     * @return de timeSpan van een appointment.
     */
    public ITimeSpan getTimeSpan()
    {
        return this.timeSpan;
    }
    
    /**
     * @return een Iterator met alle genodigden van een appointment.
     */
    public Iterator<Contact> invitees()
    {
        Iterator itr = invitees.iterator();
        return itr;
    }
    
    /**
     * @param c het contact dat wordt toegevoegd aan de appointment.
     * @return true indien het contact is toegevoegd aan de appointment, anders false.
     */
    public boolean addContact(Contact c)
    {
        while (c.appointments().hasNext())
        {
            if (c.appointments().next().timeSpan.isPartOf(timeSpan))
            {
                return false;
            }
        }
        invitees.add(c);
        return true;
    }
    
    /**
     * @param c het contact dat wordt verwijderd uit de appointment.
     */
    public void removeContact(Contact c)
    {
        if (invitees.contains(c))
        {
            invitees.remove(c);
        }
    }
}
