/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad;

import java.util.ArrayList;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import twitter4j.Status;
import wad.data.TestStatus;
import wad.jlab.logic.TwitterCrunch;
/**
 *
 * @author narck
 */
public class CrunchTest {
    
    private TwitterCrunch tw = new TwitterCrunch();
    private Calendar cal = Calendar.getInstance();
    @Test
    public void tryTimeScore() {
        
        cal.set(Calendar.MONTH,10);
        Status s1 = new TestStatus(cal.getTime());
        cal.set(Calendar.MONTH, 5);
        Status s2 = new TestStatus(cal.getTime());
        
        //ignore
        
    }
    
    @Test
    public void testDateScore() {
        
        cal.set(Calendar.DAY_OF_MONTH,10);
        Status s1 = new TestStatus(cal.getTime());
        List<Status> list = new ArrayList<>();
        list.add(s1);
        assertEquals(10,tw.getHashtagDateScore(list));
        list.add(s1);
        assertEquals(20,tw.getHashtagDateScore(list));
        
        cal.set(Calendar.DAY_OF_MONTH,23);
        Status s2 = new TestStatus(cal.getTime());    
        list.add(s2);
        assertEquals(43, tw.getHashtagDateScore(list));
    }
    
    @Test
    public void testDateWithZero() {
        // there is actually no "zeroth" day, months are 0 indexed
        cal.set(Calendar.DAY_OF_MONTH,00); 
        Status s1 = new TestStatus(cal.getTime());
        List<Status> list = new ArrayList<>();
        list.add(s1);
        
        assertEquals(30,tw.getHashtagDateScore(list));
    
    }
    
    @Test
    public void testDateWithOne() {
        // index 01 is supposed to work as expected
        cal.set(Calendar.DAY_OF_MONTH,01); 
        Status s1 = new TestStatus(cal.getTime());
        List<Status> list = new ArrayList<>();
        list.add(s1);
        
        assertEquals(1,tw.getHashtagDateScore(list));
    
    }
    
    @Test
    public void testTimeScore() {
        
        cal.set(Calendar.HOUR_OF_DAY,3);
        Status s1 = new TestStatus(cal.getTime());
        List<Status> list = new ArrayList<>();
        list.add(s1);
        assertEquals(3,tw.getHashtagTimeScore(list));
        
        cal.set(Calendar.HOUR_OF_DAY,23);
        Status s2 = new TestStatus(cal.getTime());    
        list.add(s2);
        assertEquals(26, tw.getHashtagTimeScore(list));
        
        cal.set(Calendar.HOUR_OF_DAY,10);
        Status s3 = new TestStatus(cal.getTime());    
        list.add(s3);
        assertEquals(36, tw.getHashtagTimeScore(list));
    
    }
    
    @Test
    public void testTimeWithZero() {
    
        cal.set(Calendar.HOUR_OF_DAY,00);
        Status s1 = new TestStatus(cal.getTime());
        List<Status> list = new ArrayList<>();
        list.add(s1);
        assertEquals(0,tw.getHashtagTimeScore(list));
    }
    
    @Test
    public void testMonthSuppression() {
        Calendar monthNow = cal.getInstance();
        
        cal.set(Calendar.MONTH, monthNow.get(Calendar.MONTH)-2);
        Status s1 = new TestStatus(cal.getTime());
        List<Status> list = new ArrayList<>();
        list.add(s1);
        list.add(s1);
        
        List<Status> emptyList = tw.removeOlderTweets(list);
        assertEquals(0,emptyList.size());
    }
    
    @Test
    public void testResultCrunching1() {
        
    }
    
}
