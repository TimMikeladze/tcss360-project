
package model.reviews;

import java.io.File;
import java.io.IOException;

import model.util.FileHandler;

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
    
    private File review;
    
    /**
     * Gets the paper id.
     * 
     * @return the paper id
     */
    public int getPaperID() {
        return PaperID;
    }
    
    /**
     * Gets the id of the review.
     * 
     * @return the id of the review
     */
    public int getId() {
        return id;
    }
    
    /**
     * Gets the reviewers id.
     * 
     * @return the reviewers id
     */
    public int getReviewerID() {
        return reviewerID;
    }
    
    /**
     * Gets the file extension of the review.
     * 
     * @return the file extension of the review
     */
    public String getFileExtension() {
        return fileExtension;
    }
    
    /**
     * Gets the location of the review file.
     * 
     * @return the location of the review file
     */
    public String getFile() {
        return file;
    }
    
    /**
     * Gets the review.
     * 
     * @return the review
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public File getReview() throws IOException {
        return review == null ? (review = FileHandler.convertBytesToFile(file, fileExtension)) : review;
    }
}
