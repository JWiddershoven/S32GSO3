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
public class TestTimeSpan {
   @Before public void setUp(){}
           
   @Test public void testChangeLength()
   {
    /**
     * the end time of this time span will be moved up with [minutes] minutes
     */
     Time bt = new Time(2015, 3, 10, 9, 46);
     Time et = new Time(2015, 3, 11, 9, 46);
     TimeSpan ts = new TimeSpan(bt, et);
     int length = ts.length();
     
     ts.changeLengthWith(4);
     assertEquals(length + 4, ts.length());
     assertEquals(bt, ts.getBeginTime());
     
     ts.changeLengthWith(1);
     assertEquals(length + 5, ts.length());
     assertEquals(bt, ts.getBeginTime());
     
     ts.changeLengthWith(1000000);
     assertEquals(length + 1000005, ts.length());
     assertEquals(bt, ts.getBeginTime());
     
     /* 
     * @param minutes  minutes + length of this period must be positive  
     */  
     try
     {
         length = ts.length();
         ts.changeLengthWith(-length);
         fail("minutes + length of this period must be positive.");
     }
     catch (IllegalArgumentException exc){         
     }
     
     try
     {
         length = ts.length();
         ts.changeLengthWith(-length-100);
         fail("minutes + length of this period must be positive.");
     }
     catch (IllegalArgumentException exc){
     }
     
   }
   
   @Test public void testTimeSpan()
   {
      /* class invariant: 
     * A stretch of time with a begin time and end time.
     * The end time is always later then the begin time; the length of the time span is
     * always positive
     * 
     */
       try {
           Time bt = new Time(2015, 1, 5, 12, 25);
           Time et = new Time(2015, 1, 5, 13, 45);
           TimeSpan ts = new TimeSpan(bt, et);
       } catch (IllegalArgumentException e) {
       }  
       
   }
   
   @Test(expected = IllegalArgumentException.class)
   public void testTimeSpan2()
   {
     /* class invariant: 
     * A stretch of time with a begin time and end time.
     * The end time is always later then the begin time; the length of the time span is
     * always positive
     * 
     */
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       
       TimeSpan ts = new TimeSpan(et, bt);
   }
   
   @Test public void testGetBeginTime()
   {
     /**
     * 
     * @return the begin time of this time span
     */
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       
       TimeSpan ts = new TimeSpan(bt, et);
       
       assertEquals(bt, ts.getBeginTime());
   }
   
   @Test public void testGetEndTime()
   {
     /**
     * 
     * @return the end time of this time span
     */
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       
       TimeSpan ts = new TimeSpan(bt, et);
       
       assertEquals(et, ts.getEndTime());
   }
   
   @Test public void testLength()
   {
     /**
     * beginTime will be the new begin time of this time span
     * @param beginTime must be earlier than the current end time
     * of this time span
     */
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       
       TimeSpan ts = new TimeSpan(bt, et);
       
       int expected = 80;
       int actual = ts.length();
       
       assertEquals(expected, actual);
   }
   
   @Test public void testSetBeginTime()
   {
     /**
     * beginTime will be the new begin time of this time span
     * @param beginTime must be earlier than the current end time
     * of this time span
     */
       try {
           Time bt = new Time(2015, 1, 5, 12, 25);
           Time et = new Time(2015, 1, 5, 13, 45);
           Time newbt = new Time(2015, 1, 5, 12, 30);
           
           TimeSpan ts = new TimeSpan(bt, et);
           
           ts.setBeginTime(newbt);  
       } catch (IllegalArgumentException exc) {
       }
   }
   
   @Test(expected = IllegalArgumentException.class)
   public void testSetBeginTime2()
   {
       /**
     * beginTime will be the new begin time of this time span
     * @param beginTime must be earlier than the current end time
     * of this time span
     */
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       Time newbt = new Time(2015, 1, 6, 16, 25);
       
       TimeSpan ts = new TimeSpan(bt, et);
       
       ts.setBeginTime(newbt);

   }
   
   @Test(expected = IllegalArgumentException.class)
   public void testSetEndTime()
   {
     /**
     * endTime will be the new end time of this time span
     * @param endTime must be later than the current begin time
     * of this time span
     */
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       Time newet = new Time(2015, 1, 5, 11, 25);
       
       TimeSpan ts = new TimeSpan(bt, et);
      
       ts.setEndTime(newet);
   }
   
   @Test public void testMove()
   {
     /**
     * the begin and end time of this time span will be moved up both with [minutes]
     * minutes
     * @param minutes (a negative value is allowed)
     */
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       
       TimeSpan ts = new TimeSpan(bt, et);
       
       try {
           ts.move(5);
           ts.move(-10);
       } catch (IllegalArgumentException exc) {
       }
   }
   
   @Test public void testChangeLengthWith1()
   {
     /**
     * the end time of this time span will be moved up with [minutes] minutes
     */
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       
       TimeSpan ts = new TimeSpan(bt, et);
       
       ts.changeLengthWith(5);
 
         
   }
   
   @Test(expected = IllegalArgumentException.class)
   public void testChangeLengthWith2()
   {
       /**
     * @param minutes  minutes + length of this period must be positive  
     */   
       Time bt = new Time(2015, 1, 5, 12, 25);
       Time et = new Time(2015, 1, 5, 13, 45);
       
       TimeSpan ts = new TimeSpan(bt, et);
       
       ts.changeLengthWith(-5);
   }
   
   @Test public void testIsPartOf()
   {
       TimeSpan ts1 = new TimeSpan(new Time(2015, 1, 5, 12, 25), new Time(2015, 1, 5, 13, 45));
       TimeSpan ts2 = new TimeSpan(new Time(2015, 1, 5, 13, 25), new Time(2015, 1, 5, 13, 40));
       
       assertTrue(ts1.isPartOf(ts2));
       assertFalse(ts2.isPartOf(ts1));
   }
   
   @Test public void testUnionWith()
   {
       
   }
    
}
