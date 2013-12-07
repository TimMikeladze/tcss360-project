package tests.recommendations;

import static org.junit.Assert.*;

import model.recommendations.Recommendation;

import org.junit.Test;

/**
 * Tests the Recommendation class from model.recommendations
 * 
 * @author Cathryn Castillo
 * @version December 6, 2013
 */
public class RecommendationTest {
    
    /**
     * Tests the constructor
     */
    @Test
    public void testCons() {
        assertTrue("Constructor", new Recommendation() != null);
    }

}
