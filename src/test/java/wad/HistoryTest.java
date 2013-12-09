package wad;

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
        
        //System.out.println(th.getHistory());
        //System.out.println(th.getMonth());
        
        assertEquals("asd", th.getMonthsTrending().getHashtag());
    }
    
    
    @Test
    public void testTruncatedListCorrectLength() {
        for (int i = 0; i < 30; i++) {
            th.addToHistory(new TwitterCache("#lel"));
        }
        //assertEquals(25,th.getTruncated().size());
    }
    
    @Test
    public void reversedListCorrect() {
        
    }
    
    
}
