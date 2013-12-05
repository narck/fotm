package wad;

import java.util.ArrayList;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.Test;
import twitter4j.Status;
import wad.data.TestStatus;
import wad.jlab.logic.TwitterCrunch;

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
        
        m1.put("mongo", 8);
        m1.put("maria", 8);
        assertEquals(true, tw.collisionsInScores(m1));
    }
    
    @Test
    public void testIfCollisionsCorrectWithNoCollisions() {
        
        Map<String, Integer> m1 = new TreeMap<>();
        
        m1.put("mongo", 8);
        m1.put("maria", 56);
        assertEquals(false, tw.collisionsInScores(m1));
    }
    
    @Test
    public void testRemoveCorrectLowScores() {
        Map<String, Integer> m1 = new TreeMap<>();
        
        m1.put("redis", 8);
        m1.put("mongo", 8);
        m1.put("maria", 5);
        m1.put("hadoop", 3);
        
        assertEquals(2, tw.removeNonCollidingScores(m1).size());
    }
    
    @Test
    public void correctHighestIfAllDistinct() {
         Map<String, Integer> m1 = new TreeMap<>();
        
        m1.put("redis", 2);
        m1.put("mongo", 1);
        m1.put("maria", 5);
        m1.put("hadoop", 3);
        
        tw.removeNonCollidingScores(m1);
        int i = m1.get("maria");
        assertEquals(5, i);
        
    }
    
    @Test
    public void removeTimeScoresCorrectly() {
         Map<String, Integer> m1 = new TreeMap<>();
         Map<String, Integer> m2 = new TreeMap<>();
        //datescores
        m1.put("redis", 2);
        m1.put("mongo", 1);
        m1.put("maria", 5);
        m1.put("hadoop", 5);
        //timescores
        m2.put("redis", 2);
        m2.put("mongo", 1);
        m2.put("maria", 5);
        m2.put("hadoop", 2);
        
        int i = tw.removeNonCollidingTimeScores(m1, m2).size();
        assertEquals(2, i);
         
    }
    
    @Test
    public void hashtagComparison() {
        
        SortedMap<String, Integer> m1 = new TreeMap<>();
        SortedMap<String, Integer> m2 = new TreeMap<>();
        SortedMap<String, Integer> m3 = new TreeMap<>();
        m1.put("redis", 2);
        m1.put("mongo", 1);
        m1.put("maria", 7);
        m1.put("hadoop", 17);
        
        // note that by timescore collision random tag is selected: not testable so only test without collisions
        
        assertEquals("hadoop", tw.compareHashtags(m1));
        
    }
    
    @Test
    public void testResultCrunching() {
        
        // first entry with datescores and timescores
        // datescores
        cal.set(Calendar.DAY_OF_MONTH,20);
        cal.set(Calendar.HOUR_OF_DAY,20);
        Status d1 = new TestStatus(cal.getTime());
        List<Status> dateList = new ArrayList<>();
        dateList.add(d1);
        
        cal.set(Calendar.DAY_OF_MONTH,20);
        cal.set(Calendar.HOUR_OF_DAY,19);
        Status d2 = new TestStatus(cal.getTime());    
        dateList.add(d2);
        
        // second entries
        
        cal.set(Calendar.DAY_OF_MONTH,10);
        cal.set(Calendar.HOUR_OF_DAY,20);
        Status n1 = new TestStatus(cal.getTime());
        List<Status> dateList2 = new ArrayList<>();
        dateList2.add(n1);
        
        cal.set(Calendar.DAY_OF_MONTH,10);
        cal.set(Calendar.HOUR_OF_DAY,20);
        Status n2 = new TestStatus(cal.getTime());    
        dateList2.add(n2);
        
        SortedMap<String, List<Status>> tags = new TreeMap<>();
        tags.put("redis", dateList);
        tags.put("mongo", dateList2);
        
        assertEquals("redis",tw.crunchTrendingTag(tags));
        
    }
    
    @Test
    public void testResultCrunchingDatesColliding() {
        
        // first entry with datescores and timescores
        // datescores
        cal.set(Calendar.DAY_OF_MONTH,10);
        cal.set(Calendar.HOUR_OF_DAY,20);
        Status d1 = new TestStatus(cal.getTime());
        List<Status> dateList = new ArrayList<>();
        dateList.add(d1);
        
        cal.set(Calendar.DAY_OF_MONTH,10);
        cal.set(Calendar.HOUR_OF_DAY,19);
        Status d2 = new TestStatus(cal.getTime());    
        dateList.add(d2);
        
        // second entries
        
        cal.set(Calendar.DAY_OF_MONTH,10);
        cal.set(Calendar.HOUR_OF_DAY,20);
        Status n1 = new TestStatus(cal.getTime());
        List<Status> dateList2 = new ArrayList<>();
        dateList2.add(n1);
        
        cal.set(Calendar.DAY_OF_MONTH,10);
        cal.set(Calendar.HOUR_OF_DAY,20);
        Status n2 = new TestStatus(cal.getTime());    
        dateList2.add(n2);
        
        SortedMap<String, List<Status>> tags = new TreeMap<>();
        tags.put("redis", dateList);
        tags.put("mongo", dateList2);
        
        assertEquals("mongo",tw.crunchTrendingTag(tags));
        
    }
    
    
    
}
