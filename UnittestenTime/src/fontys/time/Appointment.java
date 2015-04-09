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
public class Appointment
{

    private String subject;
    private ITimeSpan timeSpan;
    private ArrayList<Contact> invitees;

    /**
     * Er wordt een nieuwe appointment gecreëerd met een subject en timeSpan.
     *
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
     * @return true indien het contact is toegevoegd aan de appointment, anders
     * false.
     */
    public boolean addContact(Contact c)
    {
        boolean isAdded = false;
        if (c != null)
        {
            if (!c.appointments().hasNext())
            {
                invitees.add(c);
                isAdded = true;
            }
            while (c.appointments().hasNext())
            {
                if (c.appointments().next().getTimeSpan().isPartOf(timeSpan))
                {
                    isAdded = false;
                }
                else
                {
                    invitees.add(c);
                    isAdded = true;
                }
                        
            }
            
            return isAdded;
        }
        else
        {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param c het contact dat wordt verwijderd uit de appointment.
     */
    public void removeContact(Contact c)
    {
        if (c != null)
        {
            if (invitees.contains(c))
            {
                invitees.remove(c);
            }
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
