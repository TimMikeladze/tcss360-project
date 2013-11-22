
package model.reviews;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.database.Database;
import model.database.DatabaseException;
import model.permissions.Permission;
import model.util.FileHandler;

/**
 * This class manages papers for the conferences.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
 * @author Srdjan Stojcic
 * @version 11-22-2013
 */
public class ReviewManager {
    
    /**
     * Submits a review to the conference.
     * 
     * @param paperID the paper id to submit to
     * @param reviewerID the reviewer's id
     * @param file the paper's file
     * @return the id of the inserted review
     * @throws DatabaseException the database exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Permission(level = 100)
    public static int submitReview(final int paperID, final int reviewerID, final File file) throws DatabaseException, IOException {
        return Database.getInstance()
                       .createQuery(
                               "INSERT INTO reviews (PaperID, ReviewerID, SubmissionDate, File, FileExtension) VALUES (:paperID, :reviewerID, NOW(), :file, :fileExtension)")
                       .addParameter("paperID", paperID)
                       .addParameter("reviewerID", reviewerID)
                       .addParameter("file", FileHandler.convertFileToBytes(file))
                       .addParameter("fileExtension", FileHandler.getFileExtension(file))
                       .executeUpdate()
                       .getKey(Integer.class);
    }
    
    /**
     * Gets all reviews for a paper.
     * 
     * @param paperID the paper id
     * @return the reviews
     */
    @Permission(level = 100, strict = true)
    public static List<Review> getReviews(final int paperID) {
        return Database.getInstance()
                       .createQuery("SELECT ID, PaperID, ReviewerID, File, FileExtension FROM reviews WHERE PaperID = :paperID")
                       .addParameter("paperID", paperID)
                       .executeAndFetchFirst(Review.class);
    }
    
}