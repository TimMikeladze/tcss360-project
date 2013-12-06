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
 * @author Srdjan Stojcic
 * @author Jordan Matthews
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
            assertTrue("Get Recommendation is there", RecommendationManager.getRecommendation(25000) != null);
        } catch (DatabaseException e) {
            fail("Not there");
        }
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
