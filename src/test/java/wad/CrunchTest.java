/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad;

import java.util.ArrayList;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
    public void testIfCollisionsCorrectWithCollisions() {
        
        Map<String, Integer> m1 = new TreeMap<>();
        
        m1.put("lel", 8);
        m1.put("kek", 8);
        assertEquals(true, tw.collisionsInDateScores(m1));
    }
    
    @Test
    public void testIfCollisionsCorrectWithNoCollisions() {
        
        Map<String, Integer> m1 = new TreeMap<>();
        
        m1.put("lel", 8);
        m1.put("kek", 56);
        assertEquals(false, tw.collisionsInDateScores(m1));
    }
    
    @Test
    public void testRemoveCorrectLowScores() {
        Map<String, Integer> m1 = new TreeMap<>();
        
        m1.put("lel", 8);
        m1.put("kek", 8);
        m1.put("top", 5);
        m1.put("niin", 3);
        
        assertEquals(2, tw.removeNonCollidingScores(m1).size());
    }
    
    @Test
    public void correctHighestIfAllDistinct() {
         Map<String, Integer> m1 = new TreeMap<>();
        
        m1.put("lel", 2);
        m1.put("kek", 1);
        m1.put("lela", 5);
        m1.put("keka", 3);
        
        tw.removeNonCollidingScores(m1);
        int i = m1.get("lela");
        assertEquals(5, i);
        
    }
    
    @Test
    public void testResultCrunchingDates() {
        
    }
    
    @Test 
    public void testResultCrunchingScores() {
    
    }
    
    @Test
    public void testResultCrunchingComplete1() {
    
    }
    
    @Test
    public void testResultCrunchingComplete2() {
        
    }
    
}
