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
public class Contact {
    
    private String name;
    private ArrayList<Appointment> agenda;
    
    /**
     * Er wordt een nieuw contact gecreëerd met een naam.
     * Dit contact krijgt een nieuwe, lege agenda.
     * @param name naam mag geen lege string zijn.
     */
    public Contact(String name)
    {
        this.name = name;
        agenda = new ArrayList<>();
    }
    
    /** @return de naam van dit contact */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Er wordt een appointment toegevoegd aan de agenda van het contact.
     * @param a appointment moet een subject en TimeSpan bevatten.
     * @return true indien de Appointment is toegevoegd aan de agenda, anders false.
     */
    protected boolean addAppointment(Appointment a) //To-do: check TimeSpan van een appointment.
    {
        if (agenda.contains(a))
        {
            return false;
        }
        else
        {
            agenda.add(a);
            return true;
        }
    }
    /**
     * Er wordt een appointment verwijderd uit de agenda van het contact indien deze voorkomt.
     * @param a appointment moet voorkomen in de agenda van het contact.
     */
    protected void removeAppointment(Appointment a)
    {
        if (agenda.contains(a))
        {
            agenda.remove(a);
        }
    }
    /**
     * @return een Iterator met appointments van het contact.
     */
    public Iterator<Appointment> appointments()
    {
        Iterator itr = agenda.iterator();
        return itr;
    }
}
