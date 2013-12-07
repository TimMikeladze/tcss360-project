
package model.papers;

import java.io.File;
import java.util.List;

import model.database.Database;

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
    
    /*
     * Subprogram chair.
     */
    private String subprogramChair;
    
    /**
     * The paper file
     */
    private File paper;
    
    /**
     * The conference name;
     */
    private String conferenceName;
    
    /**
     * recommended
     */
    private int recommended;
    
    /**
     * username
     */
    private String username;
    
    /**
     * Paper from id
     * 
     * <dt><b>Precondition:</b><dd> requires paperID > 0;
     * <dt><b>Postcondition:</b><dd> ensures Paper is returned.
     * @param paperID papers id
     * @return the paper
     */
    public static Paper paperFromID(final int paperID) {
        List<Paper> results = Database
                .getInstance()
                .createQuery(
                        "SELECT p.ConferenceID, p.ID AS PaperID, p.Title, p.Description, p.AuthorID, p.SubmissionDate, p.Status, p.Revised, p.FileExtension, CONVERT(p.File USING utf8) AS File, p.RevisionDate, p.Recommended, CONCAT(u.Firstname, ' ', u.Lastname) AS Username FROM papers AS p JOIN users AS u ON u.ID = p.AuthorID WHERE p.ID = :paperID")
                .addParameter("paperID", paperID).executeAndFetch(Paper.class);
        return Database.hasResults(results) ? results.get(0) : null;
        
    }
    
    /**
     * Returns the papers id.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A paper id is returned
     * @return the papers id
     */
    public int getPaperID() {
        return paperID;
    }
    
    /**
     * Returns the conferences id.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A conference id is returned
     * @return the conferences id.
     */
    public int getConferenceID() {
        return conferenceID;
    }
    
    /**
     * Returns the papers title.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A title is returned
     * @return the papers title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Returns the papers description.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A description is returned
     * @return the papers description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Returns the papers submission date.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A submission date is returned
     * @return the papers submission date
     */
    public String getSubmissionDate() {
        return submissionDate.toString().split("\\s+")[0].toString();
    }
    
    /**
     * Returns the paper authors id.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A author id is returned
     * @return the paper authors id
     */
    public int getAuthorID() {
        return authorID;
    }
    
    /**
     * Subprogram chair
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A subprogram chair is returned
     * @return subprogram chair
     */
    public String getSubprogramChair() {
        return subprogramChair;
    }
    
    /**
     * Returns the status of the paper (accpeted=2/rejected=1/undecided=0).
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A status is returned
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
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A revised is returned
     * @return whether the paper was revised or not
     */
    public int getRevised() {
        return revised;
    }
    
    /**
     * Returns the papers file extension.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A file extension is returned
     * @return the papers file extension
     */
    public String getFileExtension() {
        return fileExtension;
    }
    
    /**
     * Returns the content of the file.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A content is returned
     * @return the content of the file.
     */
    public String getFile() {
        return file;
    }
    
    /**
     * Returns the papers revision date.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A revision date is returned
     * @return the papers revision date
     */
    public String getRevisionDate() {
        return revisionDate.toString().split("\\s+")[0].toString();
    }
    
    /**
     * Gets the conference name
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A conference name is returned
     * @return the conference name
     */
    public String getConferenceName() {
        return conferenceName;
    }
    
    /**
     * is recommended 
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A boolean is returned
     * @return boolean
     */
    public boolean isRecommended() {
        return recommended == 1;
    }
    
    /**
     * is recommended string
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A string is returned
     * @return string
     */
    public String isRecommendedString() {
        String isRecommended = "No";
        if (recommended == 1) {
            isRecommended = "Yes";
        }
        return isRecommended;
    }
    
    /**
     * is accepted
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A boolean is returned
     * @return accepted
     */
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
    
    /**
     * A username
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A username is returned
     * @return user name
     */
    public String getUsername() {
        return username;
    }
    
}
