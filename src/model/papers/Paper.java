
package model.papers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.database.Database;
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
    private String submissionDate;
    
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
    private String revisionDate;
    
    private String subprogramChair;
    
    /**
     * The paper file
     */
    private File paper;
    
    /**
     * The conference name;
     */
    private String conferenceName;
    
    private int recommended;
    
    private String username;
    
    public static Paper paperFromID(final int paperID) {
        List<Paper> results = Database.getInstance()
                                      .createQuery(
                                              "SELECT p.ConferenceID, p.ID AS PaperID, p.Title, p.Description, p.AuthorID, p.SubmissionDate, p.Status, p.Revised, p.FileExtension, p.File, p.RevisionDate, p.Recommended, CONCAT(u.Firstname, ' ', u.Lastname) AS Username FROM papers AS p JOIN users AS u ON u.ID = p.AuthorID WHERE p.ID = :paperID")
                                      .addParameter("paperID", paperID)
                                      .executeAndFetch(Paper.class);
        return Database.hasResults(results) ? results.get(0) : null;
        
    }
    
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
    public String getSubmissionDate() {
        return submissionDate.toString()
                             .split("\\s+")[0].toString();
    }
    
    /**
     * Returns the paper authors id.
     * 
     * @return the paper authors id
     */
    public int getAuthorID() {
        return authorID;
    }
    
    public String getSubprogramChair() {
        return subprogramChair;
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
    public String getRevisionDate() {
        return revisionDate.toString()
                           .split("\\s+")[0].toString();
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
    
    /**
     * Gets the conference name
     * 
     * @return the conference name
     */
    public String getConferenceName() {
        return conferenceName;
    }
    
    public boolean isRecommended() {
        return recommended == 1;
    }
    
    public String isRecommendedString() {
        String isRecommended = "No";
        if (recommended == 1) {
            isRecommended = "Yes";
        }
        return isRecommended;
    }
    
    public String isAccepted() {
        String isAccepted = "Undecided";
        if (status == 2) {
            isAccepted = "Yes";
        }
        else if (status == 1) {
            isAccepted = "No";
        }
        return isAccepted;
    }
    
    public String getUsername() {
        return username;
    }
    
}
