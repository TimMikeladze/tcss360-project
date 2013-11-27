package tests.recommendations;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.database.Database;
import model.database.DatabaseException;
import model.recommendations.Recommendation;
import model.recommendations.RecommendationManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the RecommendationManager class.
 * 
 * @author Srdjan Stojcic
 * @version 11.26.2013
 *
 */

public class RecommendationManagerTest {
	
	/**
	 * The paper ID.
	 */
	private int paperID;
	
	/**
	 * The reviewer ID.
	 */
	private int reviewerID;
	
	/**
	 * The review file.
	 */
	private File file;
	
	/**
	 * Sets up the tests.
	 */
	@BeforeClass
	public void setup() {
		paperID = 100;
		reviewerID = 10;
		try {
			file = File.createTempFile("review", ".docx");
		} catch (IOException e) {
			System.out.println("Failed to create temp file");
			e.printStackTrace();
		}
	}
	
	/**
	 * Cleans up the tests.
	 */
	@AfterClass
	public void cleanUp() {
		// Remove recommendation from database.
        Database.getInstance().createQuery("DELETE FROM paper_recommendations WHERE paperID = :paperID AND reviewerID = :reviewerID")
                .addParameter("paperID", paperID)
                .addParameter("reviewerID", reviewerID).executeUpdate();
	}

	
	/**
	 * Tests the submitRecommendation method.
	 */
	@Test
	public void testSubmitRecommendation() {
		try {
			RecommendationManager.submitRecommendation(paperID, reviewerID, file);
		} catch (IOException e) {
			System.out.println("Failed to submit a recommendation during testSubmitRecommendation");
			e.printStackTrace();
		}
		
		List<Recommendation> list = Database.getInstance()
                .createQuery(
                        "SELECT ID, PaperID, ReviewerID, File, FileExtension FROM paper_recommendations WHERE PaperID = :paperID")
                .addParameter("paperID", paperID)
                .executeAndFetch(Recommendation.class);
		
		assertTrue(Database.hasResults(list));
		
	}

	/**
	 * Tests the getRecommendation method.
	 */
	@Test
	public void testGetRecommendation() {
		List<Recommendation> list = RecommendationManager.getRecommendations(paperID);
		if (Database.hasResults(list)) {
			assertTrue(true);
		} else {
			fail("Did not successfully get the recommendation");
		}
		
	}
	
	/**
	 * Tests the getRecommendation method in the case where it should throw an exception (no recommendation exists).
	 */
	@Test
	public void testGetRecommendationException() {
		try {
            // This recommendation does not exist, so it should throw an exception.
            RecommendationManager.getRecommendation(18341394);
            fail("Should have thrown an SQLException");
        }
        catch (DatabaseException e) {
            assertTrue(true);
        }
	}

}
