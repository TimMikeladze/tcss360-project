package tests.recommendations;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;


import model.database.Database;
import model.database.DatabaseException;
import model.recommendations.RecommendationManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the RecommendationManager class from model.recommendations
 * 
 * @author Cathryn Castillo
 * @author Tim Mikeladze (did testSubmitRecommendation)
 * @author Mohammad Jume (did testGetRecommendation)
 * @version 11.26.2013
 *
 */
public class RecommendationManagerTest {
	/**
	 * The recommendation ID
	 */
	private int recID;

	/**
	 * Sets up the tests
	 */
	@Before
	public void setUp() {
	    recID = 0;
	    RecommendationManager temp = new RecommendationManager();
	    temp.hashCode();
	}
	
	/**
	 * Tests the submitRecommendation method
	 */
	@Test
	public void testSubmitRecommendation() {
	    try {
            recID = RecommendationManager.submitRecommendation(25000, 35000, new File("tests/paper.txt"));
        } catch (IOException e) {
            fail("Did not work");
        }
	    assertTrue("Recommendation submitted", recID > 0);
	}
	
	/**
	 * Tests the getRecommendation method
	 */
	@Test
	public void testGetRecommendation() {
	    try {
            RecommendationManager.getRecommendation(25000);
        } catch (DatabaseException e) {
            assertEquals("Get Recommendation is not there", e.getLocalizedMessage(), "A recommendation hasn't been submitted for this paper");
        }
	}
	
	/**
     * Tests the getRecommendation method
     */
    @Test
    public void testGetRecommendations() {
            assertTrue("Is recommended", RecommendationManager.getRecommendations(0) != null);

    }
    
	/**
	 * Cleans up the tests.
	 */
	@After
	public void cleanUp() {
		// Remove recommendation from database.
        Database.getInstance()
                .createQuery("DELETE FROM paper_recommendations WHERE ID = :paperID")
                .addParameter("paperID", recID)
                .executeUpdate();
	}
}