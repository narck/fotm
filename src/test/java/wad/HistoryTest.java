package wad;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import wad.jlab.data.TwitterCache;
import wad.jlab.repo.TwitterHistory;
import static org.junit.Assert.*;

/**
 *
 * @author narck
 */
public class HistoryTest {
   
    private TwitterHistory th = new TwitterHistory();
    
    @Test
    public void testCorrectMonthOutput() {
        
        th.addToHistory(new TwitterCache("#asd"));
        th.addToHistory(new TwitterCache("#asd"));
        th.addToHistory(new TwitterCache("#kek"));
        th.addToHistory(new TwitterCache("#lel"));
        
        assertEquals("asd", th.getMonthsTrending().getHashtag());
    }
    
    
    @Test
    public void testTruncatedListCorrectLength() {
        for (int i = 0; i < 30; i++) {
            th.addToHistory(new TwitterCache("#lel"));
        }
        assertEquals(10,th.getTruncated().size());
    }
    
    @Test
    public void reversedListCorrect() {
        ArrayList<TwitterCache> l = new ArrayList<>();
        l.add(new TwitterCache("asd"));
        l.add(new TwitterCache("asdf"));
        l.add(new TwitterCache("asdg"));
        
        
        for (TwitterCache twitterCache : l) {
            th.addToHistory(twitterCache);  
        }
        
        Collections.reverse(l);
        assertEquals(l, th.getTruncated());
        
    }
    
    @Test
    
    public void limitExceededCorrectly() {
        for (int i = 0; i < 101; i++) {
            th.addToHistory(new TwitterCache("#lel"));
        }
        
        assertEquals(100,th.getHistory().size());
        
    }
    
    
}
