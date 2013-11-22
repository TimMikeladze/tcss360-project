
package tests.papers;

import static org.junit.Assert.assertEquals;

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
 * Tests the PaperManager Class
 * 
 * @author Jordan Matthews
 * 
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
     * The program chair id
     */
    private int programChairID;
    /**
     * The conference ID
     */
    private int conferenceID;
    /**
     * The user ID
     */
    private int userID;
    
    /**
     * Initializes the Author for the tests.
     */
    @Before
    public void initializeUser() {
        firstName = "Jon";
        lastName = "Snow";
        email = "youknownothingjonsnow@gmail.com";
        
        // Add the user 'Jon Snow' to the database
        // Need to remove from database
        userID = Database.getInstance()
                         .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                         .addParameter("firstName", firstName)
                         .addParameter("lastName", lastName)
                         .addParameter("email", email)
                         .executeUpdate()
                         .getKey(Integer.class);
    }
    
    /**
     * Initializes a conference for testing.
     */
    @Before
    public void initializeConference() {
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
                .addParameter("userID", programChairID)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission())
                .executeUpdate();
    }
    
    /**
     * Testing the submission of 3 papers
     * 
     * @throws IOException
     */
    
    @Test
    public void testSubmitPapersLessThanFour() {
        try {
            // add three papers
            PaperManager.submitPaper(conferenceID, userID, "The Title1", "The Description1", new File("tests/paper.txt"));
            //PaperManager.submitPaper(conferenceID, userID, "The Title2", "The Description2", file);
            //PaperManager.submitPaper(conferenceID, userID, "The Title3", "The Description3", file);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // get the list of papers
        List<Paper> test = PaperManager.getPapers(conferenceID);
        assertEquals("The paper is in the list", "The Description1", test.get(0)
                                                                         .getDescription()
                                                                         .toString());
        //assertEquals("The paper is in the list", "The Description2", test.get(1).getDescription());
        //assertEquals("The paper is in the list", "The Description3", test.get(2).getDescription());
    }
    
    /**
     * Tests the submission if the author has submitted too many papers
     * 
     * @throws IOException
     */
    /*
    @Test
    public void shouldThrowDatabaseExceptionWhenSubmitPapersReachesMax() {
        try {
            PaperManager.submitPaper(conferenceID, userID, "The Title4", "The Description4", new File("src/tests/papers/test.txt"));
            PaperManager.submitPaper(conferenceID, userID, "The Title5", "The Description5", new File("src/tests/papers/test.txt"));
            PaperManager.submitPaper(conferenceID, userID, "The Title6", "The Description6", new File("src/tests/papers/test.txt"));
            PaperManager.submitPaper(conferenceID, userID, "The Title7", "The Description7", new File("src/tests/papers/test.txt"));
            fail("Should have thrown DataBaseException but did not!");
        } catch (final DatabaseException e ) {
            final String msg = "You've submitted the maximum amount of papers allowed into this conference";
            assertEquals("The max has been reached", msg, e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    */
    
    @After
    // TODO take down the user as well, and submitted papers
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
                .addParameter("userID", userID)
                .executeUpdate();
    }
}
