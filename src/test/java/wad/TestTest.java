package wad;

import org.junit.Test;
import static org.junit.Assert.*;
import wad.jlab.logic.HitEvaluator;

public class TestTest {
    
    @Test
    public void sample() {
        int result = 1 + 1;
        assertNotEquals("1+1 should not be three", 3, result);
    }
    
    
    @Test
    public void sampleTwo() {
        int result = 1 + 1;
        assertNotEquals("1+1 should not be three", 3, result);
    }
    
    @Test
    public void seeResult() {
        assertEquals(true, true);
    }
    
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
    
    public void tryValidUrl2() {
        assertTrue(new HitEvaluator().setUrl("http://amazon.com"));
    }
    
    /** Tests:
     * Connection?
     * No result?
     * Failed parsing?
     * Verify if at least one?
     **/
}

