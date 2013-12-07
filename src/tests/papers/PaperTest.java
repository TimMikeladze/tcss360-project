package tests.papers;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;

import model.database.Database;
import model.papers.Paper;
import model.papers.PaperStatus;
import model.permissions.PermissionLevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * Tests the Paper class from model.papers
 * 
 * @author Jordan Matthews
 * @version December 5, 2013
 */
public class PaperTest {
    /**
     * Conference name
     */
    private String name;
    /**
     * Conference location
     */
    private String location;
    /**
     * Conference ID
     */
    private int conferenceID;
    /**
     * User firstname
     */
    private String firstName;
    /**
     * User last name
     */
    private String lastName;
    /**
     * User email
     */
    private String email;
    /**
     * UserID of the user
     */
    private int userID1;
    /**
     * paper id for the user
     */
    private int paperID;
    /**
     * Paper object
     */
    private Paper paper;
    
    /**
     * Sets up the class
     */
    @Before
    public void setUp() {
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
        name = "Conference1";
        location = "Seattle";
        conferenceID = Database.getInstance()
                .createQuery("INSERT INTO conferences (Name, Location, Date) VALUES (:name, :location, :date)")
                .addParameter("name", name)
                .addParameter("location", location)
                .addParameter("date", new Date())
                .executeUpdate()
                .getKey(Integer.class);

        Database.getInstance()
                .createQuery("INSERT INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:id, :userID, :permissionID)")
                .addParameter("id", conferenceID)
                .addParameter("userID", userID1)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission())
                .executeUpdate();
        paperID = Database.getInstance()
                .createQuery(
                        "INSERT INTO papers (ConferenceID, AuthorID, Title, Description, SubmissionDate, RevisionDate, File, FileExtension) VALUES (:conferenceID, :authorID, :title, :description, NOW(), NOW(), :file, :fileExtension)")
                .addParameter("conferenceID", conferenceID)
                .addParameter("authorID", userID1)
                .addParameter("title", "The title")
                .addParameter("description", "the description")
                .addParameter("file", new File("tests/paper.txt"))
                .addParameter("fileExtension", "/path.txt")
                .executeUpdate()
                .getKey(Integer.class);
        paper = Paper.paperFromID(paperID);
    }
    
    /**
     * Tests the paperFromId() method
     */
    @Test
    public void testPaperFromID() {
        assertEquals("Paper not found", paper.getPaperID(), paperID);
        assertTrue("Paper is not null", Paper.paperFromID(Integer.MAX_VALUE) == null);
    }
    
    /**
     * Tests all of the getters
     */
    @Test
    public void testGetters() {
        assertEquals("Getter not correct", paper.getAuthorID(), userID1);
        assertEquals("Getter not correct", paper.getConferenceID(), conferenceID);
        assertEquals("Getter not correct", paper.getTitle(), "The title");
        assertEquals("Getter not correct", paper.getConferenceName(), null);
        assertEquals("Getter not correct", paper.getDescription(), "the description");
        assertTrue("Getter not correct", paper.getFile() != null);
        assertTrue("Getter not correct", paper.getUsername() != null);
        assertEquals("Getter not correct", paper.getFileExtension(), "/path.txt");
        assertEquals("Getter not correct", paper.getRevised(), 0);
        assertEquals("Submission Date", paper.getSubmissionDate(), paper.getRevisionDate());
        assertNull(paper.getSubprogramChair());
    }
    
    /**
     * Tests the getPaperMethod
     */
    //Finish this method
    /*
    @Test
    public void testGetPaper() {
        try {
            assertTrue("Paper not valid", paper.getPaper().exists());
        } catch (IOException e) {
            fail("The paper was not found");
        }
    }
    */
    
    /**
     * Tests the isRecommended method
     */
    @Test
    public void testIsRecommended() {
        assertEquals("Is recommended", paper.isRecommended(), false);

        Database.getInstance()
            .createQuery("UPDATE papers SET Recommended = 1 WHERE ID = :paperID")
            .addParameter("paperID", paperID)
            .executeUpdate();
        paper = Paper.paperFromID(paperID);
        assertEquals("Is recommended", paper.isRecommended(), true);
    }
    
    /**
     * Tests the isRecommendedString method
     */
    @Test
    public void testIsRecommendedString() {
        assertEquals("Is recommended", paper.isRecommendedString(), "No");
        Database.getInstance()
            .createQuery("UPDATE papers SET Recommended = 1 WHERE ID = :paperID")
            .addParameter("paperID", paperID)
            .executeUpdate();
        paper = Paper.paperFromID(paperID);
        assertEquals("Is recommended", paper.isRecommendedString(), "Yes");
    }
    
    /**
     * Tests the getStatus() method
     */
    @Test
    public void testGetStatus() {
        assertEquals("Paper undecided", paper.getStatus(), PaperStatus.UNDECIDED);
        Database.getInstance()
            .createQuery("UPDATE papers SET Status = :paperStatus WHERE ID = :id")
            .addParameter("paperStatus", 1).addParameter("id", paperID)
            .executeUpdate();
        paper = Paper.paperFromID(paperID);
        assertEquals("Paper accepted", paper.getStatus(), PaperStatus.REJECTED);
        Database.getInstance()
            .createQuery("UPDATE papers SET Status = :paperStatus WHERE ID = :id")
            .addParameter("paperStatus", 2).addParameter("id", paperID)
            .executeUpdate();
        paper = Paper.paperFromID(paperID);
        assertEquals("Paper accepted", paper.getStatus(), PaperStatus.ACCEPTED);
    }
    
    /**
     * Tests the isAccepted() method
     */
    @Test
    public void testIsAccepted() {
        assertEquals("Undecided", paper.isAccepted(), "Undecided");
        Database.getInstance()
            .createQuery("UPDATE papers SET Status = 2 WHERE ID = :id")
            .addParameter("id", paperID)
            .executeUpdate();
        paper = Paper.paperFromID(paperID);
        assertEquals("Undecided", paper.isAccepted(), "Yes");
        Database.getInstance()
            .createQuery("UPDATE papers SET Status = 1 WHERE ID = :id")
            .addParameter("id", paperID)
            .executeUpdate();
        paper = Paper.paperFromID(paperID);
        assertEquals("Undecided", paper.isAccepted(), "No");
    }
    
    /**
     * Cleanup the test class
     */
    @After
    public void takeDown() {
        Database.getInstance()
            .createQuery("DELETE FROM conferences WHERE ID = :conferenceID")
            .addParameter("conferenceID", conferenceID)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM users WHERE ID = :userID")
            .addParameter("userID", userID1)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM papers WHERE ID = :paperID")
            .addParameter("paperID", paperID)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
            .addParameter("conferenceID", conferenceID)
            .addParameter("userID", userID1)
            .executeUpdate();
    }
}
