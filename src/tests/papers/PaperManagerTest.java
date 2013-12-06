package tests.papers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import model.database.Database;
import model.database.DatabaseException;
import model.papers.Paper;
import model.papers.PaperManager;
import model.permissions.PermissionLevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the PaperManager Class from models.papers
 *
 * @author Jordan Matthews
 * @author Srdjan Stojcic
 */
public class PaperManagerTest {
    /**
     * User's first name for testing.
     */
    private String firstName;
    /**
     * User's last name for testing.
     */
    private String lastName;
    /**
     * User's email for testing.
     */
    private String email;
    /**
     * Conference Name
     */
    private String name;
    /**
     * Conference location
     */
    private String location;
    /**
     * The conference ID
     */
    private int conferenceID;
    /**
     * The first user ID
     */
    private int userID1;
    /**
     * The second user ID
     */
    private int userID2;
    /**
     * The third user ID
     */
    private int userID3;
    /**
     * List of submitted papers
     */
    List<Paper> paperList;
    /**
     * The paper ID
     */
    private int paperID;
    /**
     * Initializes the Author for the tests.
     */
    @Before
    public void initialize() {
        firstName = "Jon";
        lastName = "Snow";
        email = "youknownothingjonsnow@gmail.com";

        // Add the user 'Jon Snow' to the database
        userID1 = Database.getInstance()
                          .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                          .addParameter("firstName", firstName)
                          .addParameter("lastName", lastName)
                          .addParameter("email", email)
                          .executeUpdate()
                          .getKey(Integer.class);
        // Add the user 'Daenerys Targaryen' to the database
        firstName = "Daenerys";
        lastName = "Targaryen";
        email = "ilikedragons@gmail.com";
        userID2 = Database.getInstance()
                          .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                          .addParameter("firstName", firstName)
                          .addParameter("lastName", lastName)
                          .addParameter("email", email)
                          .executeUpdate()
                          .getKey(Integer.class);
        firstName = "the";
        lastName = "king";
        email = "theking@gmail.com";
        userID3 = Database.getInstance()
                          .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                          .addParameter("firstName", firstName)
                          .addParameter("lastName", lastName)
                          .addParameter("email", email)
                          .executeUpdate()
                          .getKey(Integer.class);
        name = "Conference1";
        location = "Seattle";
        conferenceID = Database.getInstance()
                               .createQuery("INSERT INTO conferences (Name, Location, Date) VALUES (:name, :location, :date)")
                               .addParameter("name", name)
                               .addParameter("location", location)
                               .addParameter("date", new Date(1420099200000l))
                               .executeUpdate()
                               .getKey(Integer.class);

        Database.getInstance()
                .createQuery("INSERT INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:id, :userID, :permissionID)")
                .addParameter("id", conferenceID)
                .addParameter("userID", userID1)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission())
                .executeUpdate();
        Database.getInstance()
                .createQuery("INSERT INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:id, :userID, :permissionID)")
                .addParameter("id", conferenceID)
                .addParameter("userID", userID2)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission())
                .executeUpdate();
        Database.getInstance()
                .createQuery("INSERT INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:id, :userID, :permissionID)")
                .addParameter("id", conferenceID)
                .addParameter("userID", userID3)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission())
                .executeUpdate();
        paperID = Database.getInstance()
                .createQuery("INSERT INTO papers (ConferenceID, AuthorID, Title, Description, SubmissionDate, File, FileExtension) VALUES (:conferenceID, :authorID, :title, :description, NOW(), :file, :fileExtension)")
                .addParameter("conferenceID", conferenceID)
                .addParameter("authorID", userID2)
                .addParameter("title", "The title")
                .addParameter("description", "the description")
                .addParameter("file", new File("tests/paper.txt"))
                .addParameter("fileExtension", "/path.txt")
                .executeUpdate()
                .getKey(Integer.class);
        PaperManager temp = new PaperManager();
        temp.hashCode();
    }

    /**
     * Testing the submission of 3 papers
     */
    @Test
    public void testSubmitPapersLessThanFour() {
        try {
            // add a paper
            PaperManager.submitPaper(conferenceID, userID3, "The Title1", "The Description1", new File("tests/paper.txt"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        // get the list of papers
        paperList = PaperManager.getPapers(conferenceID);
        paperList.get(0);
        assertEquals("The paper is in the list", "the description", paperList.get(0)
                                                                              .getDescription()
                                                                              .toString());
    }

    /**
     * Tests the submission if the author has submitted too many papers
     *
     */
    @Test
    public void shouldThrowDatabaseExceptionWhenSubmitPapersReachesMax() {
        try {
            PaperManager.submitPaper(conferenceID, userID3, "The Title4", "The Description4", new File("tests/paper.txt"));
            PaperManager.submitPaper(conferenceID, userID3, "The Title5", "The Description5", new File("tests/paper.txt"));
            PaperManager.submitPaper(conferenceID, userID3, "The Title6", "The Description6", new File("tests/paper.txt"));
            PaperManager.submitPaper(conferenceID, userID3, "The Title7", "The Description7", new File("tests/paper.txt"));
            PaperManager.submitPaper(conferenceID, userID3, "The Title8", "The Description8", new File("tests/paper.txt"));
            fail("Should have thrown DataBaseException but did not!");
        }
        catch (final DatabaseException e) {
            final String msg = "You've submitted the maximum amount of papers allowed into this conference";
            assertEquals("The max has been reached", msg, e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the method assignPaperToSubprogramChair() & getAssignedPapersForSubprogramChair()
     * from PaperManager
     */
    /*
    @Test
    public void testAssignPaperToSubprogramChair() {
        try {
            PaperManager.assignPaper(paperID, userID2, PermissionLevel.PROGRAM_CHAIR);
            assertEquals("The paper has been assigned to the subprogram chair", paperID,
                    PaperManager.getAssignedPapersForSubprogramChair(conferenceID, userID2)
                                .get(0)
                                .getPaperID());
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }

    }
    */
    /**
     * Tests the getPaperAuthorID method.
     */
    @Test
    public void testGetPaperAuthorID() {
    	try {
			assertEquals("The paper's author ID test.", PaperManager.getPaperAuthorID(paperID), userID2);
		} catch (DatabaseException e) {
			System.out.println("Error: Unable to get paper author ID.");
			e.printStackTrace();
		}
    }

    /**
     * Remove all of the entries to the database that were used for testing
     */
    @After
    public void takeDown() {
        Database.getInstance()
                .createQuery("DELETE FROM conferences WHERE ID = :conferenceID")
                .addParameter("conferenceID", conferenceID)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM papers WHERE ConferenceID = :conferenceID")
                .addParameter("conferenceID", conferenceID)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
                .addParameter("conferenceID", conferenceID)
                .addParameter("userID", userID1)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
                .addParameter("conferenceID", conferenceID)
                .addParameter("userID", userID2)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
                .addParameter("conferenceID", conferenceID)
                .addParameter("userID", userID3)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM assigned_papers WHERE PaperID = :paperID AND UserID = :userID")
                .addParameter("paperID", conferenceID)
                .addParameter("userID", userID2)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM papers WHERE ID = :paperID")
                .addParameter("paperID", paperID)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM users WHERE ID = :userID")
                .addParameter("userID", userID1)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM users WHERE ID = :userID")
                .addParameter("userID", userID2)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM users WHERE ID = :userID")
                .addParameter("userID", userID3)
                .executeUpdate();
    }
}
