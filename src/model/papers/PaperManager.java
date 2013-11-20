package model.papers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.List;

import model.conferences.ConferenceManager;
import model.database.Database;
import model.database.DatabaseException;
import model.database.Errors;
import model.permissions.Permission;
import model.permissions.PermissionLevel;
import model.reviews.Review;

/**
 * The Class PaperManager.
 */
public class PaperManager {

    private static final int MAX_PAPERS = 4;

    /**
     * Submits a paper to the conference
     * 
     * @param conferenceID
     *            the conference id to submit to
     * @param authorID
     *            the author's id
     * @param title
     *            the paper title
     * @param description
     *            the paper description
     * @param file
     *            the paper's file
     * @throws DatabaseException
     */
    @Permission(level = 100)
    public static void submitPaper(int conferenceID, int authorID,
            String title, String description, File file)
            throws DatabaseException {
        // TODO needs to check if papers submission isn't past conference date
        // TODO convert file to bytes and store in database
    	byte[] convertedFile = convertFileToBytes(file);
    	
        if (MAX_PAPERS > getNumberOfSubmittedPapers(conferenceID, authorID)) {
            Database.getInstance()
                    .createQuery(
                            "INSERT INTO papers (ConferenceID, AuthorID, Title, Description, SubmissionDate, File) VALUES (:conferenceID, :authorID, :title, :description, NOW(), :file)")
                    .addParameter("conferenceID", conferenceID)
                    .addParameter("authorID", authorID)
                    .addParameter("title", title)
                    .addParameter("description", description)
                    .addParameter("file", convertedFile).executeUpdate()
                    .getKey(Integer.class);
            ConferenceManager.addUserToConference(conferenceID, authorID,
                    PermissionLevel.AUTHOR);
        } else {
            throw new DatabaseException(Errors.MAX_PAPER_SUBMISSIONS_EXCEEDED);
        }
    }
    
    /**
     * Converts a file to byte array.
     * 
     * @param f The file to convert
     * @return A byte array representation of the given file.
     */
    private static byte[] convertFileToBytes(File f) {
    	FileInputStream fis = null;
    	byte[] byteArray = new byte[(int) f.length()];
    	
    	try {
    		// convert file into array of bytes
    		fis = new FileInputStream(f);
    		fis.read(byteArray);
    		fis.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		return byteArray;
    }
    
    @Permission(level = 400)
    public static List<Paper> getAssignedPapersForSubprogramChair(int userID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT p.ConferenceID, p.ID AS PaperID, p.Title, p.Description, p.AuthorID, p.SubmissionDate, p.Status, p.Revised, p.FileExtension, p.File, p.RevisionDate FROM papers AS p JOIN assigned_papers AS a ON a.PaperID = p.ID WHERE a.UserID = :userID")
                .addParameter("userID", userID).executeAndFetch(Paper.class);
    }
    
    /*
     * TODO whatever you just said. Something about a left join maybe?
     */
    @Permission(level = 200)
    public static List<Paper> getAssignedPapersForReviewer(int userID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT p.ConferenceID, p.ID AS PaperID, p.Title, p.Description, p.AuthorID, p.SubmissionDate, p.Status, p.Revised, p.FileExtension, p.File, p.RevisionDate FROM papers AS p JOIN assigned_papers AS a ON a.PaperID = p.ID WHERE a.UserID = :userID")
                .addParameter("userID", userID).executeAndFetch(Paper.class);
    }
    
    @Permission(level = 400)
    public static void assignPaperToSubprogramChair(final int paperID, final int userID) {
        Database.getInstance().createQuery("INSERT IGNORE INTO assigned_papers (PaperID, UserID) VALUES (:paperID, :userID)")
                .addParameter("paperID", paperID)
                .addParameter("userID", userID)
                .executeUpdate();
    }
    
    @Permission(level = 400)
    public static void assignPaperToReviewer(final int paperID, final int userID) {
        Database.getInstance().createQuery("INSERT IGNORE INTO assigned_papers (PaperID, UserID) VALUES (:paperID, :userID)")
                .addParameter("paperID", paperID)
                .addParameter("userID", userID)
                .executeUpdate();
    }
    
    public static List<Review> getReviews(final int conferenceID, final int authorID) {
        return Database.getInstance().createQuery("SELECT r.ID, r.PaperID, r.ReviewerID, r.File, r.FileExtension, p.Status FROM reviews AS r JOIN papers p.ID = r.PaperID WHERE p.ConferenceID = :conferenceID AND p.AuthorID = :authorID AND p.Status != 0").addParameter("conferenceID", conferenceID).addParameter("authorID", authorID).executeAndFetch(Review.class);
    }

    /**
     * Submits a review to the conference
     * 
     * @param paperID
     *            the paper id to submit to
     * @param reviewerID
     *            the reviewer's id
     * @param file
     *            the paper's file
     * @throws DatabaseException
     */
    @Permission(level = 200)
    public static void submitReview(int paperID, int reviewerID, File file)
            throws DatabaseException {
        // TODO needs to check if papers submission isn't past conference date
        // TODO convert file to bytes and store in database
    	byte[] convertedFile = convertFileToBytes(file);
    	
        Database.getInstance()
                .createQuery(
                        "INSERT INTO reviews (PaperID, ReviewerID, SubmissionDate, File) VALUES (:paperID, :reviewerID, NOW(), :file)")
                .addParameter("paperID", paperID)
                .addParameter("reviewerID", reviewerID)
                .addParameter("file", convertedFile).executeUpdate();
    }

    @Permission(level = 400)
    public static void acceptPaper(int paperID) {
        setPaperStatus(paperID, true);
    }

    @Permission(level = 400)
    public static void rejectPaper(int paperID) {
        setPaperStatus(paperID, false);
    }

    private static void setPaperStatus(int paperID, boolean status) {
        int paperStatus = status ? 2 : 1;
        Database.getInstance()
                .createQuery(
                        "UPDATE papers SET Status = :paperStatus WHERE ID = :id")
                .addParameter("paperStatus", paperStatus)
                .addParameter("id", paperID).executeUpdate();
    }

    @Permission(level = 400)
    public static List<Paper> getPapers(int conferenceID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT ConferenceID, ID AS PaperID, Title, Description, AuthorID, SubmissionDate, Status, Revised, FileExtension, File, RevisionDate FROM papers WHERE ConferenceID = :conferenceID")
                .addParameter("conferenceID", conferenceID)
                .executeAndFetch(Paper.class);
    }

    /**
     * Gets the number of papers an author has submitted to a conference
     * 
     * @param conferenceID
     *            the conference id
     * @param authorID
     *            the authors id
     * @return number of papers author has submitted
     */
    public static int getNumberOfSubmittedPapers(int conferenceID, int authorID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT COUNT(1) FROM papers WHERE ConferenceID = :conferenceID AND AuthorID = :authorID")
                .addParameter("conferenceID", conferenceID)
                .addParameter("authorID", authorID).executeAndFetchTable()
                .rows().get(0).getInteger(0);
    }
}
