package model.reviews;

import java.sql.Timestamp;

public class Review {
    
    private int PaperID;
    
    private int id;
    
    private int reviewerID;
            
    private int status;
        
    private String fileExtension;
    
    private String file;

    public int getPaperID() {
        return PaperID;
    }

    public int getId() {
        return id;
    }

    public int getReviewerID() {
        return reviewerID;
    }

    public int getStatus() {
        return status;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Review [getPaperID()=" + getPaperID() + ", getId()=" + getId()
                + ", getReviewerID()=" + getReviewerID() + ", getStatus()="
                + getStatus() + ", getFileExtension()=" + getFileExtension()
                + ", getFile()=" + getFile() + "]";
    }
    
    
}
