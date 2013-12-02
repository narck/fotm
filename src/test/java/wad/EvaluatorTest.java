package wad;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import wad.jlab.logic.HitEvaluator;
import wad.jlab.logic.TwitterEvaluator;

public class EvaluatorTest {
        
    @Test
    public void tryInvalidUrl() {
        assertFalse(new HitEvaluator().setUrl("123asdkek"));
    }
    
    
    @Test
    public void tryInvalidUrl2() {
        assertFalse(new HitEvaluator().setUrl("niin.com"));
    }
    
    
    @Test
    public void tryInvalidUrl3() {
        assertFalse(new HitEvaluator().setUrl("http://yes"));
    }
    
    @Test
    public void tryValidUrl() {
        assertTrue(new HitEvaluator().setUrl("http://yle.fi"));
    }
    
    @Test
    public void tryValidUrl2() {
        assertTrue(new HitEvaluator().setUrl("http://amazon.com"));
    }
    
    /**
     * Twitter class tests
     */
    
    
    @Test
    public void checkConnectionIfInvalidUrl() {
        String badUrl = "asd";
        TwitterEvaluator te = new TwitterEvaluator();
        assertEquals(te.checkConnection(badUrl), false);
        
    }
    @Ignore
    @Test(timeout=5000)
    public void checkConnectionIfCorrectUrl() {
        String url = "http://www.google.com";
        TwitterEvaluator te = new TwitterEvaluator();
        assertEquals(te.checkConnection(url), true); // presumably! add real connection testing
        
    }
    
    
    
    /** Tests:
     * Connection?
     * No result?
     * Failed parsing?
     * Verify if at least one?
     **/
}

