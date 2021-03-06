
package model.reviews;

import java.io.File;
import java.util.List;

import model.database.Database;

/**
 * This class holds all the information for a review.
 * 
 * @author Mohammad Juma
 * @version 11-18-2013
 */
public class Review {
    
    /**
     * The paper id the review is for.
     */
    private int PaperID;
    
    /**
     * The id of the review.
     */
    private int id;
    
    /**
     * The reviewers id.
     */
    private int reviewerID;
    
    /**
     * The file extension of the review.
     */
    private String fileExtension;
    
    /**
     * The file location of the review.
     */
    private String file;
    
    /**
     * review
     */
    private File review;
    
    /**
     * review from id
     * 
     * <dt><b>Precondition:</b><dd> requires id > 0;
     * <dt><b>Postcondition:</b><dd> ensures A review is returned
     * @param id the id
     * @return the review
     */
    public static Review reviewFromID(final int id) {
        List<Review> results = Database
                .getInstance()
                .createQuery(
                        "SELECT ID, PaperID, ReviewerID, CONVERT(File USING utf8) AS File, FileExtension FROM reviews WHERE ID = :id")
                .addParameter("id", id).executeAndFetch(Review.class);
        return Database.hasResults(results) ? results.get(0) : null;
    }
    
    /**
     * Gets the paper id.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A paper id is returned
     * @return the paper id
     */
    public int getPaperID() {
        return PaperID;
    }
    
    /**
     * Gets the id of the review.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures An id is returned
     * @return the id of the review
     */
    public int getID() {
        return id;
    }
    
    /**
     * Gets the reviewers id.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A reviwers id is returned
     * @return the reviewers id
     */
    public int getReviewerID() {
        return reviewerID;
    }
    
    /**
     * Gets the file extension of the review.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A review is returned
     * @return the file extension of the review
     */
    public String getFileExtension() {
        return fileExtension;
    }
    
    /**
     * Gets the location of the review file.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A file is returned
     * @return the location of the review file
     */
    public String getFile() {
        return file;
    }
}
