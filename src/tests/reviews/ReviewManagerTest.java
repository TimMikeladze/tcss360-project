package tests.reviews;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.database.Database;
import model.database.DatabaseException;
import model.reviews.Review;
import model.reviews.ReviewManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ReviewManager Class from model.reviews
 * 
 * @author Tim Mikeladze
 * @version December 5, 2013
 */
public class ReviewManagerTest {
    /**
     * The paper ID
     */
    private int paperID;
    /**
     * The review number
     */
    private int reviewID;
    /**
     * Sets up the class for testing
     */
    @Before
    public void setUp() {
        paperID = Database.getInstance()
                .createQuery(
                        "INSERT INTO papers (ConferenceID, AuthorID, Title, Description, SubmissionDate, RevisionDate, File, FileExtension) VALUES (:conferenceID, :authorID, :title, :description, NOW(), NOW(), :file, :fileExtension)")
                .addParameter("conferenceID", 15000)
                .addParameter("authorID", 25000)
                .addParameter("title", "The title")
                .addParameter("description", "the description")
                .addParameter("file", new File("tests/paper.txt"))
                .addParameter("fileExtension", "/path.txt")
                .executeUpdate()
                .getKey(Integer.class);
        ReviewManager temp = new ReviewManager();
        temp.hashCode();
    }
    
    /**
     * Tests the SubmitReview Method
     */
    @Test
    public void testSubmitReview() {
        int reviewID = 0;
        try {
            reviewID = ReviewManager.submitReview(paperID, 26000, new File("tests/paper.txt"));
        } catch (DatabaseException | IOException e) {
            fail("Could not");
        }
        assertTrue("Review is there", reviewID > 0);
        
        try {
            ReviewManager.submitReview(paperID, 25000, new File("tests/paper.txt"));
        } catch (DatabaseException | IOException e) {
            assertEquals("The reviewer is the same", e.getLocalizedMessage(), "A user can't review a paper that they authored");
        }   
    }
    
    /**
     * Tests the getReviews method
     */
    @Test
    public void testGetReviews() {
        List<Review> reviews = ReviewManager.getReviews(paperID);
        assertTrue("Reviews exist", reviews != null);
    }
    
    /**
     * Tests getSubmittedReviews
     */
    @Test
    public void testGetSubmittedReviews() {
        assertTrue("Submitted Reviews exist", ReviewManager.getSubmittedReview(paperID, 25000) == null);
    }
    
    /**
     * Tests the isReviewed method
     */
    @Test
    public void testIsReviewed() {
        assertEquals("Is reviewed", ReviewManager.isReviewed(paperID, 25000), false);
    }
    
    /**
     * Cleans up the class when done testing
     */
    @After
    public void cleanUp() {
        Database.getInstance()
            .createQuery("DELETE FROM papers WHERE ID = :paperID")
            .addParameter("paperID", paperID)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM reviews WHERE ID = :paperID")
            .addParameter("paperID", reviewID)
            .executeUpdate();
    }
}
