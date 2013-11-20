package tests.papers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import model.database.Database;
import model.database.DatabaseException;
import model.papers.Paper;
import model.papers.PaperManager;

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
     * Conference date
     */
    private Date date;
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
        userID = Database
                .getInstance()
                .createQuery(
                        "INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                .addParameter("firstName", firstName)
                .addParameter("lastName", lastName)
                .addParameter("email", email).executeUpdate()
                .getKey(Integer.class);
    }
    
    /**
     * Initializes a conference for testing.
     */
    @Before
    public void initializeConference() {
        name = "Conference1";
        location = "Seattle";
        date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        programChairID = 0001;
        conferenceID = Database
                .getInstance()
                .createQuery(
                        "INSERT INTO conferences (Name, Location, Date, ProgramChairID) VALUES (:name, :location, :date, :programChairID)")
                .addParameter("name", name).addParameter("location", location)
                .addParameter("date", sqlDate)
                .addParameter("programChairID", programChairID).executeUpdate()
                .getKey(Integer.class);
    }
    /**
     * Testing the submission of 3 papers
     */
    @Test
    public void testSubmitPapersLessThanFour() {
        try {
            // add three papers
            PaperManager.submitPaper(conferenceID, userID, "The Title1", "The Description1", new File(""));
            PaperManager.submitPaper(conferenceID, userID, "The Title2", "The Description2", new File(""));
            PaperManager.submitPaper(conferenceID, userID, "The Title3", "The Description3", new File(""));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // get the list of papers
        List<Paper> test = PaperManager.getPapers(conferenceID);
        assertEquals("The paper is in the list", "The Description1", test.get(0).getDescription());
        assertEquals("The paper is in the list", "The Description2", test.get(1).getDescription());
        assertEquals("The paper is in the list", "The Description3", test.get(2).getDescription());
    }
    
    /**
     * Tests the submission if the author has submitted too many papers
     */
    @Test
    public void shouldThrowDatabaseExceptionWhenSubmitPapersReachesMax() {
        try {
            PaperManager.submitPaper(conferenceID, userID, "The Title4", "The Description4", new File(""));
            fail("Should have thrown DataBaseException but did not!");
        } catch (final DatabaseException e ) {
            final String msg = "You've submitted the maximum amount of papers allowed into this conference";
            assertEquals("The max has been reached", msg, e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
