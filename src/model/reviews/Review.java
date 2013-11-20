
package model.reviews;

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
     * The status of the review.
     */
    private int status;
    
    /**
     * The file extension of the review.
     */
    private String fileExtension;
    
    /**
     * The file location of the review.
     */
    private String file;
    
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
     * Gets the status of the review.
     * 
     * @return the status of the review
     */
    public int getStatus() {
        return status;
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
     * Gets the toString of the review.
     * 
     * @return all relevant information about the review
     */
    @Override
    public String toString() {
        return "Review [getPaperID()=" + getPaperID() + ", getId()=" + getId()
                + ", getReviewerID()=" + getReviewerID() + ", getStatus()=" + getStatus()
                + ", getFileExtension()=" + getFileExtension() + ", getFile()=" + getFile()
                + "]";
    }
    
}
