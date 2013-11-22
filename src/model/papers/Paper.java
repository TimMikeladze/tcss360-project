
package model.papers;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import model.util.FileHandler;

/**
 * This class holds all the information for a paper.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
 * @version 11-18-2013
 */
public class Paper {
    
    /**
     * The papers id.
     */
    private int paperID;
    
    /**
     * The conferences id.
     */
    private int conferenceID;
    
    /**
     * The title of the paper.
     */
    private String title;
    
    /**
     * The description of the paper.
     */
    private String description;
    
    /**
     * The papers submission date.
     */
    private Timestamp submissionDate;
    
    /**
     * The authors id.
     */
    private int authorID;
    
    /**
     * The papers status (accpeted=2/rejected=1/undecided=0).
     */
    private int status;
    
    /**
     * The revision status of the paper.
     */
    private int revised;
    
    /**
     * The papers file extension.
     */
    private String fileExtension;
    
    /**
     * The file.
     */
    private String file;
    
    /**
     * The revision date.
     */
    private Timestamp revisionDate;
    
    private File paper;
    
    /**
     * Returns the papers id.
     * 
     * @return the papers id
     */
    public int getPaperID() {
        return paperID;
    }
    
    /**
     * Returns the conferences id.
     * 
     * @return the conferences id.
     */
    public int getConferenceID() {
        return conferenceID;
    }
    
    /**
     * Returns the papers title.
     * 
     * @return the papers title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Returns the papers description.
     * 
     * @return the papers description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Returns the papers submission date.
     * 
     * @return the papers submission date
     */
    public Timestamp getSubmissionDate() {
        return submissionDate;
    }
    
    /**
     * Returns the paper authors id.
     * 
     * @return the paper authors id
     */
    public int getAuthorID() {
        return authorID;
    }
    
    /**
     * Returns the status of the paper (accpeted=2/rejected=1/undecided=0).
     * 
     * @return the status of the paper
     */
    public PaperStatus getStatus() {
        PaperStatus status = PaperStatus.UNDECIDED;
        if (this.status == 1) {
            status = PaperStatus.REJECTED;
        }
        else if (this.status == 2) {
            status = PaperStatus.ACCEPTED;
        }
        return status;
    }
    
    /**
     * Returns whether the paper was revised or not.
     * 
     * @return whether the paper was revised or not
     */
    public int getRevised() {
        return revised;
    }
    
    /**
     * Returns the papers file extension.
     * 
     * @return the papers file extension
     */
    public String getFileExtension() {
        return fileExtension;
    }
    
    /**
     * Returns the papers file location.
     * 
     * @return the papers file location
     */
    public String getFile() {
        return file;
    }
    
    /**
     * Returns the papers revision date.
     * 
     * @return the papers revision date
     */
    public Timestamp getRevisionDate() {
        return revisionDate;
    }
    
    /**
     * Gets the paper.
     * 
     * @return the paper
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public File getPaper() throws IOException {
        return paper == null ? (paper = FileHandler.convertBytesToFile(file, fileExtension)) : paper;
        
    }
    
}
