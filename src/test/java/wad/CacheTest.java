package wad;

import org.junit.Test;
import wad.jlab.data.TwitterCache;
import static org.junit.Assert.*;

public class CacheTest {

    
    @Test
    public void correctSubstring() {
        TwitterCache tc = new TwitterCache("#wasd");
        
        assertEquals(tc.getHashtagWithHash(), "#wasd");
        assertEquals(tc.getHashtag(), "wasd");
    }
    
    public void correctNotTimeout() {
        TwitterCache tc = new TwitterCache("#wasd");
        
        assertTrue(tc.hasTimedOut());
        
    }
    
    
}
