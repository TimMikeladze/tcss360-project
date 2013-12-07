package tests.reviews;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import model.database.Database;
import model.reviews.Review;
import model.util.FileHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Review Class from model.reviews
 * 
 * @author Jordan Matthews
 * @version December 5, 2013
 */
public class ReviewTest {
    /**
     * The review ID
     */
    private int reviewID;
    
    /**
     * Sets up the class for testing
     * @throws IOException 
     */
    @Before
    public void setUp() throws IOException {
        reviewID = Database.getInstance()
            .createQuery(
                    "INSERT INTO reviews (PaperID, ReviewerID, File, FileExtension) VALUES (:paperID, :reviewerID, :file, :fileExtension)")
            .addParameter("paperID", 15000)
            .addParameter("reviewerID", 25000)
            .addParameter("file", FileHandler.convertFileToBytes(new File("tests/paper.txt")))
            .addParameter("fileExtension", "txt")
            .executeUpdate()
            .getKey(Integer.class);
    }
    
    /**
     * Tests the reviewFromID method
     */
    @Test
    public void testReviewFromID() {
        assertEquals("Review is found", Review.reviewFromID(reviewID).getID(), reviewID);
        assertEquals("Review not found", Review.reviewFromID(Integer.MAX_VALUE), null);
    }
    
    /**
     * Tests the getters
     */
    @Test
    public void testGetters() {
        assertTrue("Review File is found", Review.reviewFromID(reviewID).getFile() != null);
        assertTrue("Review Extension is found", Review.reviewFromID(reviewID).getFileExtension() != null);
        assertEquals("Review is found", Review.reviewFromID(reviewID).getPaperID(), 15000);
        assertEquals("Review is found", Review.reviewFromID(reviewID).getReviewerID(), 25000);
    }
    
    /**
     * Tests the getReviewMethod
     */
    //Finish this method
    /*
    @Test
    public void testgetReview() {
        try {
            assertTrue("Review not valid", Review.reviewFromID(reviewID).getReview().exists());
        } catch (IOException e) {
            fail("The paper was not found");
        }    
    }
    */
    
    /**
     * Cleans up the class after testing
     */
    @After
    public void takeDown() {
        Database.getInstance()
            .createQuery("DELETE FROM reviews WHERE ID = :userID")
            .addParameter("userID", reviewID)
            .executeUpdate();
    }
}
