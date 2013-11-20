
package model.papers;

import java.io.File;
import java.util.List;

import model.conferences.ConferenceManager;
import model.database.Database;
import model.database.DatabaseException;
import model.database.Errors;
import model.permissions.Permission;
import model.permissions.PermissionLevel;
import model.reviews.Review;

/**
 * This class PaperManager.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
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
     * @throws DatabaseException
     */
    @Permission(level = 100)
    public static void submitPaper(final int conferenceID, final int authorID,
            final String title, final String description, final File file)
            throws DatabaseException {
        // TODO needs to check if papers submission isn't past conference date
        // TODO convert file to bytes and store in database
        if (MAX_PAPERS > getNumberOfSubmittedPapers(conferenceID, authorID)) {
            Database.getInstance()
                    .createQuery(
                            "INSERT INTO papers (ConferenceID, AuthorID, Title, Description, SubmissionDate) VALUES (:conferenceID, :authorID, :title, :description, NOW())")
                    .addParameter("conferenceID", conferenceID)
                    .addParameter("authorID", authorID).addParameter("title", title)
                    .addParameter("description", description).executeUpdate()
                    .getKey(Integer.class);
            ConferenceManager.addUserToConference(conferenceID, authorID,
                    PermissionLevel.AUTHOR);
        }
        else {
            throw new DatabaseException(Errors.MAX_PAPER_SUBMISSIONS_EXCEEDED);
        }
    }
    
    /**
     * Gets all the papers that were assigned to a Subprogram Chair in a conference.
     * 
     * @param userID The Subprogram Chairs id.
     * @return the list of papers
     */
    @Permission(level = 400)
    public static List<Paper> getAssignedPapersForSubprogramChair(final int userID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT p.ConferenceID, p.ID AS PaperID, p.Title, p.Description, p.AuthorID, p.SubmissionDate, p.Status, p.Revised, p.FileExtension, p.File, p.RevisionDate FROM papers AS p JOIN assigned_papers AS a ON a.PaperID = p.ID WHERE a.UserID = :userID")
                .addParameter("userID", userID).executeAndFetch(Paper.class);
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
        return Database
                .getInstance()
                .createQuery(
                        "SELECT p.ConferenceID, p.ID AS PaperID, p.Title, p.Description, p.AuthorID, p.SubmissionDate, p.Status, p.Revised, p.FileExtension, p.File, p.RevisionDate FROM papers AS p JOIN assigned_papers AS a ON a.PaperID = p.ID WHERE a.UserID = :userID")
                .addParameter("userID", userID).executeAndFetch(Paper.class);
    }
    
    /**
     * Assigns a paper to a subprogram chair.
     * 
     * @param paperID the papers id
     * @param userID the Subprogram Chairs id
     */
    @Permission(level = 400)
    public static void assignPaperToSubprogramChair(final int paperID, final int userID) {
        Database.getInstance()
                .createQuery(
                        "INSERT IGNORE INTO assigned_papers (PaperID, UserID) VALUES (:paperID, :userID)")
                .addParameter("paperID", paperID).addParameter("userID", userID)
                .executeUpdate();
    }
    
    /**
     * 
     * Assigns a paper to a reviewer.
     * 
     * @param paperID the papers id
     * @param userID the reviewers id
     */
    @Permission(level = 400)
    public static void assignPaperToReviewer(final int paperID, final int userID) {
        Database.getInstance()
                .createQuery(
                        "INSERT IGNORE INTO assigned_papers (PaperID, UserID) VALUES (:paperID, :userID)")
                .addParameter("paperID", paperID).addParameter("userID", userID)
                .executeUpdate();
    }
    
    /**
     * Returns all the reviews for an author.
     * 
     * @param conferenceID The conference id the reviews are in
     * @param authorID The authors id
     * @return The list of reviews
     */
    public static List<Review> getReviews(final int conferenceID, final int authorID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT r.ID, r.PaperID, r.ReviewerID, r.File, r.FileExtension, p.Status FROM reviews AS r JOIN papers p.ID = r.PaperID WHERE p.ConferenceID = :conferenceID AND p.AuthorID = :authorID AND p.Status != 0")
                .addParameter("conferenceID", conferenceID).addParameter("authorID", authorID)
                .executeAndFetch(Review.class);
    }
    
    /**
     * Submits a review to the conference.
     * 
     * @param paperID the paper id to submit to
     * @param reviewerID the reviewer's id
     * @param file the paper's file
     * @throws DatabaseException
     */
    @Permission(level = 200)
    public static void submitReview(final int paperID, final int reviewerID, final File file)
            throws DatabaseException {
        // TODO needs to check if papers submission isn't past conference date
        // TODO convert file to bytes and store in database
        Database.getInstance()
                .createQuery(
                        "INSERT INTO papers (PaperID, ReviewerID, SubmissionDate) VALUES (:paperID, :reviewerID, NOW())")
                .addParameter("paperID", paperID).addParameter("reviewerID", reviewerID)
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
                .addParameter("paperStatus", paperStatus).addParameter("id", paperID)
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
        return Database
                .getInstance()
                .createQuery(
                        "SELECT ConferenceID, ID AS PaperID, Title, Description, AuthorID, SubmissionDate, Status, Revised, FileExtension, File, RevisionDate FROM papers WHERE ConferenceID = :conferenceID")
                .addParameter("conferenceID", conferenceID).executeAndFetch(Paper.class);
    }
    
    /**
     * Gets the number of papers an author has submitted to a conference.
     * 
     * @param conferenceID the conference id
     * @param authorID the authors id
     * @return number of papers author has submitted
     */
    public static int getNumberOfSubmittedPapers(final int conferenceID, final int authorID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT COUNT(1) FROM papers WHERE ConferenceID = :conferenceID AND AuthorID = :authorID")
                .addParameter("conferenceID", conferenceID).addParameter("authorID", authorID)
                .executeAndFetchTable().rows().get(0).getInteger(0);
    }
}
