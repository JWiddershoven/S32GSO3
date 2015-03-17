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
 * @author Jelle
 */
public class TestTime {
    
@Before public void setUp(){}
    

@Test public void TestTime() {
    //creation of a Time-object with year y, month m, day d, hours h and
    //minutes m; if the combination of y-m-d refers to a non-existing date 
    //a correct value of this Time-object will be not guaranteed 

     //@param m 1≤m≤12
    try 
    {
        Time t1 = new Time(2015, 13, 3, 11, 46);
        fail("month 1≤m≤12");
    }
    catch (IllegalArgumentException exc){
        
    }
    
    try 
    {
        Time t2 = new Time(2015, 0, 3, 11, 46);
        fail("month 1≤m≤12");
    }
    catch (IllegalArgumentException exc){
        
    }
    
     //@param d 1≤d≤31
    try
    {
        Time t3 = new Time(2015, 3, 32, 11, 48);
        fail("day 1≤d≤31");
    }
    catch (IllegalArgumentException exc){
        
    }
    
    try
    {
        Time t4 = new Time(2015, 3, 0, 11, 48);
        fail("day 1≤d≤31");
    }
    catch (IllegalArgumentException exc){
        
    }
    
     //@param h 0≤h≤23
    try
    {
        Time t5 = new Time(2015, 10, 3, 24, 49);
        fail("hour 0≤h≤23");
    }
    catch (IllegalArgumentException exc){
        
    }
    
    try
    {
        Time t6 = new Time(2015, 10, 3, -1, 49);
        fail("hour 0≤h≤23");
    }
    catch (IllegalArgumentException exc){
        
    }
    
     //@param min 0≤m≤59
    try
    {
        Time t7 = new Time(2015, 10, 3, 11, 60);
        fail("minutes 0≤m≤59");
    }
    catch (IllegalArgumentException exc){
        
    }
    
    try
    {
        Time t8 = new Time(2015, 10, 3, 11, -1);
        fail("minutes 0≤m≤59");
    }
    catch (IllegalArgumentException exc){
        
    }
}

@Test public void testGetDayInWeek()
{
    try
    {
        Time time = new Time(2015, 3, 17, 14, 8);
        time.getDayInWeek();
        
    }
    catch (IllegalArgumentException exc){
        fail("Ongeldige datum");
    }
    
    try
    {
        Time time = new Time(2015, 3, 31, 14, 8);
        time.getDayInWeek();
    }
    catch (IllegalArgumentException exc){
        fail("Ongeldige datum");
    }
    
    try
    {
        Time time = new Time(2015, 3, 1, 14, 8);
        time.getDayInWeek();
    }
    catch (IllegalArgumentException exc){
        fail("Ongeldige datum");
    }
}

@Test public void testPlus()
{
    try
    {
        ITime time = new Time(2015, 3, 17, 14, 15);
        time = time.plus(5);
        assertEquals(20, time.getMinutes());
    }
    catch (IllegalArgumentException exc)
    {
        
    }
    
    try
    {
        ITime time = new Time(2015, 3, 17, 14, 53);
        time = time.plus(6);
        assertEquals(59, time.getMinutes());
    }
    catch (IllegalArgumentException exc){
        
    }
    
    try
    {
        ITime time = new Time(2015, 3, 17, 14, 53);
        time = time.plus(-3);
        assertEquals(50, time.getMinutes());
    }
    catch (IllegalArgumentException exc){
        
    }
    
    try
    {
        ITime time = new Time(2015, 3, 17, 14, 55);
        time = time.plus(-56);
        assertEquals(59, time.getMinutes());
    }
    catch (IllegalArgumentException exc){
        
    }
}

}
