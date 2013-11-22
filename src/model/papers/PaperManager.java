
package model.papers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.conferences.ConferenceManager;
import model.database.Database;
import model.database.DatabaseException;
import model.database.Errors;
import model.permissions.Permission;
import model.permissions.PermissionLevel;
import model.util.FileHandler;

/**
 * This class manages papers for the conferences.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
 * @author Srdjan Stojcic
 * @version 11-18-2013
 */
public class PaperManager {
    
    /**
     * The maximum number of papers that can be submitted to a conference.
     */
    private static final int MAX_PAPERS = 4;
    
    /**
     * Submits a paper to the conference.
     * 
     * @param conferenceID the conference id to submit to
     * @param authorID the author's id
     * @param title the paper title
     * @param description the paper description
     * @param file the paper's file
     * @throws DatabaseException the database exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Permission(level = 100)
    public static void submitPaper(final int conferenceID, final int authorID, final String title, final String description, final File file)
            throws DatabaseException, IOException {
        if (MAX_PAPERS > getNumberOfSubmittedPapers(conferenceID, authorID)) {
            Database.getInstance()
                    .createQuery(
                            "INSERT INTO papers (ConferenceID, AuthorID, Title, Description, SubmissionDate, File, FileExtension) VALUES (:conferenceID, :authorID, :title, :description, NOW(), :file, :fileExtension)")
                    .addParameter("conferenceID", conferenceID)
                    .addParameter("authorID", authorID)
                    .addParameter("title", title)
                    .addParameter("description", description)
                    .addParameter("file", FileHandler.convertFileToBytes(file))
                    .addParameter("fileExtension", FileHandler.getFileExtension(file))
                    .executeUpdate()
                    .getKey(Integer.class);
            ConferenceManager.addUserToConference(conferenceID, authorID, PermissionLevel.AUTHOR);
        }
        else {
            throw new DatabaseException(Errors.MAX_PAPER_SUBMISSIONS_EXCEEDED);
        }
    }
    
    /**
     * Removes the paper.
     * 
     * @param paperID the paper's id
     */
    @Permission(level = 100)
    public static void removePaper(final int paperID) {
        Database.getInstance()
                .createQuery("DELETE FROM papers WHERE ID = :paperID")
                .addParameter("paperID", paperID)
                .executeUpdate();
    }
    
    /**
     * Gets all the papers that were assigned to a Subprogram Chair in a conference.
     * 
     * @param userID he user id of the Subprogram Chair
     * @return list of papers
     */
    @Permission(level = 400)
    public static List<Paper> getAssignedPapersForSubprogramChair(final int userID) {
        return Database.getInstance()
                       .createQuery(
                               "SELECT p.ConferenceID, p.ID AS PaperID, p.Title, p.Description, p.AuthorID, p.SubmissionDate, p.Status, p.Revised, p.FileExtension, p.File, p.RevisionDate FROM papers AS p JOIN assigned_papers AS a ON a.PaperID = p.ID WHERE a.UserID = :userID")
                       .addParameter("userID", userID)
                       .executeAndFetch(Paper.class);
    }
    
    /**
     * Gets all the papers that were assigned to a reviewer in a conference.
     * 
     * @param userID The user id of the reviewer
     * @return The list of papers
     */
    // TODO whatever you just said. Something about a left join maybe?
    @Permission(level = 200)
    public static List<Paper> getAssignedPapersForReviewer(final int userID) {
        return Database.getInstance()
                       .createQuery(
                               "SELECT p.ConferenceID, p.ID AS PaperID, p.Title, p.Description, p.AuthorID, p.SubmissionDate, p.Status, p.Revised, p.FileExtension, p.File, p.RevisionDate FROM papers AS p JOIN assigned_papers AS a ON a.PaperID = p.ID WHERE a.UserID = :userID")
                       .addParameter("userID", userID)
                       .executeAndFetch(Paper.class);
    }
    
    /**
     * Assign paper to a user.
     * 
     * @param paperID the paper id
     * @param userID the user id
     * @param permission the user's permission level
     */
    @Permission(level = 300)
    public static void assignPaper(final int paperID, final int userID, final PermissionLevel permission) {
        Database.getInstance()
                .createQuery("INSERT IGNORE INTO assigned_papers (PaperID, UserID, PermissionID) VALUES (:paperID, :userID, :permissionID)")
                .addParameter("paperID", paperID)
                .addParameter("userID", userID)
                .addParameter("permissionID", permission.getPermission())
                .executeUpdate();
    }
    
    /**
     * Changes the status of a paper to accepted.
     * 
     * @param paperID the papers id
     */
    @Permission(level = 400)
    public static void acceptPaper(final int paperID) {
        setPaperStatus(paperID, true);
    }
    
    /**
     * Changes the status of the paper to rejected.
     * 
     * @param paperID the papers id
     */
    @Permission(level = 400)
    public static void rejectPaper(final int paperID) {
        setPaperStatus(paperID, false);
    }
    
    /**
     * Sets the papers status.
     * 
     * @param paperID the papers id
     * @param status the status of the paper
     */
    private static void setPaperStatus(final int paperID, final boolean status) {
        int paperStatus = status ? 2 : 1;
        Database.getInstance()
                .createQuery("UPDATE papers SET Status = :paperStatus WHERE ID = :id")
                .addParameter("paperStatus", paperStatus)
                .addParameter("id", paperID)
                .executeUpdate();
    }
    
    /**
     * Gets all the papers in a conference.
     * 
     * @param conferenceID the conferences id
     * @return the list of papers
     */
    @Permission(level = 400)
    public static List<Paper> getPapers(final int conferenceID) {
        return Database.getInstance()
                       .createQuery(
                               "SELECT ConferenceID, ID AS PaperID, Title, Description, AuthorID, SubmissionDate, Status, Revised, FileExtension, File, RevisionDate FROM papers WHERE ConferenceID = :conferenceID")
                       .addParameter("conferenceID", conferenceID)
                       .executeAndFetch(Paper.class);
    }
    
    /**
     * Gets the number of papers an author has submitted to a conference.
     * 
     * @param conferenceID the conference id
     * @param authorID the authors id
     * @return number of papers author has submitted
     */
    public static int getNumberOfSubmittedPapers(final int conferenceID, final int authorID) {
        return Database.getInstance()
                       .createQuery("SELECT COUNT(1) FROM papers WHERE ConferenceID = :conferenceID AND AuthorID = :authorID")
                       .addParameter("conferenceID", conferenceID)
                       .addParameter("authorID", authorID)
                       .executeAndFetchTable()
                       .rows()
                       .get(0)
                       .getInteger(0);
    }
}