
package model.reviews;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.database.Database;
import model.database.DatabaseErrors;
import model.database.DatabaseException;
import model.papers.PaperManager;
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
     * Submits a review for a paper.
     * 
     * <dt><b>Precondition:</b><dd> requires paperID > 0;
     * <dt><b>Postcondition:</b><dd> ensures A review is submitted
     * @param paperID the paper id to submit to
     * @param reviewerID the reviewer's id
     * @param file the paper's file
     * @return the id of the inserted review
     * @throws DatabaseException the database exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Permission(level = 200)
    public static int submitReview(final int paperID, final int reviewerID, final File file)
            throws DatabaseException, IOException {
        if (PaperManager.getPaperAuthorID(paperID) != reviewerID) {
            return Database
                    .getInstance()
                    .createQuery(
                            "INSERT INTO reviews (PaperID, ReviewerID, File, FileExtension) VALUES (:paperID, :reviewerID, :file, :fileExtension)")
                    .addParameter("paperID", paperID).addParameter("reviewerID", reviewerID)
                    .addParameter("file", FileHandler.convertFileToBytes(file))
                    .addParameter("fileExtension", FileHandler.getFileExtension(file))
                    .executeUpdate().getKey(Integer.class);
        }
        else {
            throw new DatabaseException(DatabaseErrors.CANT_REVIEW_PAPER);
        }
    }
    
    /**
     * Gets all reviews for a paper.
     * 
     * <dt><b>Precondition:</b><dd> requires paperID > 0;
     * <dt><b>Postcondition:</b><dd> ensures A list is returned
     * @param paperID the paper id
     * @return the reviews
     */
    @Permission(level = 100, strict = true)
    public static List<Review> getReviews(final int paperID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT ID, PaperID, ReviewerID, CONVERT(File USING utf8) AS File, FileExtension FROM reviews WHERE PaperID = :paperID")
                .addParameter("paperID", paperID).executeAndFetch(Review.class);
    }
    
    /**
     * Gets the submitted reviews submitted by the reviewer for a paper
     * 
     * <dt><b>Precondition:</b><dd> requires paperID > 0;
     * <dt><b>Postcondition:</b><dd> ensures A review is returned
     * @param paperID the paper's id
     * @param userID the reviewer's id
     * @return the submitted reviews
     */
    @Permission(level = 200)
    public static Review getSubmittedReview(final int paperID, final int userID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT ID, PaperID, ReviewerID, CONVERT(File USING utf8) AS File, FileExtension FROM reviews WHERE PaperID = :paperID AND reviewerID = :userID")
                .addParameter("paperID", paperID).addParameter("userID", userID)
                .executeAndFetchFirst(Review.class);
    }
    
    /**
     * is reviewed 
     * 
     * <dt><b>Precondition:</b><dd> requires paperId > 0;
     * <dt><b>Postcondition:</b><dd> ensures A boolean is returned
     * @param paperID paper id
     * @param userID user id
     * @return boolean
     */
    public static boolean isReviewed(final int paperID, final int userID) {
        return Database
                .hasResults(Database
                        .getInstance()
                        .createQuery(
                                "SELECT 1 FROM reviews WHERE PaperID = :paperID AND reviewerID = :userID")
                        .addParameter("paperID", paperID).addParameter("userID", userID)
                        .executeAndFetch(Review.class));
    }
}