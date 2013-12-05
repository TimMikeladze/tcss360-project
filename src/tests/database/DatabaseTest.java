package tests.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.database.Database;
import model.permissions.PermissionLevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.data.Table;

/**
 * Tests the Database class from models.database
 * 
 * @author Jordan Matthews
 * @version December 4, 2013
 */
public class DatabaseTest {
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
     * The first user ID
     */
    private int userID;
    
    /**
     * Sets up the class for testing
     */
    @Before
    public void setUp() {
        firstName = "Jon";
        lastName = "Snow";
        email = "youknownothingjonsnow@gmail.com";

        // Add the user 'Jon Snow' to the database
        userID = Database.getInstance()
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
                .addParameter("userID", userID)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission())
                .executeUpdate();
    }
    
    /**
     * Tests the getInstance method
     */
    @Test
    public void testGetInstance() {
        assertTrue("Not a sql object", Sql2o.class.isInstance(Database.getInstance()));
        Database temp = null;
        assertTrue("Not a sql object", Sql2o.class.isInstance(temp.getInstance()));
    }
    
    /**
     * Tests the HasResults method with the list parameter
     */
    @Test
    public void testHasResultsList() {
        List<Integer> temp = new ArrayList<Integer>();
        temp.add(1);
        assertTrue("Has results", !Database.hasResults(new ArrayList<Integer>()));
        assertTrue("Has results", Database.hasResults(temp));
        temp = null;
        assertFalse("Has results", Database.hasResults(temp));
    }
    
    /**
     * Tests the HasResults method with the table parameter
     */
    @Test
    public void testHasResultsTable() {
        Table t = Database.getInstance()
                .createQuery("SELECT COUNT(1) FROM conferences WHERE ID = :id")
                .addParameter("id", conferenceID)
                .executeAndFetchTable();
        assertTrue("Has results", Database.hasResults(t));
        t = Database.getInstance()
                .createQuery("SELECT * FROM conferences WHERE ID = :id")
                .addParameter("id", Integer.MAX_VALUE)
                .executeAndFetchTable();
        assertTrue("Has results", !Database.hasResults(t));
        t = null;
        assertFalse("Has results", Database.hasResults(t));
    }

    /**
     * Tests the isConnected method
     */
    @Test
    public void testIsConnected() {
        assertTrue("Not connected", Database.isConnected());
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
                .createQuery("DELETE FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
                .addParameter("conferenceID", conferenceID)
                .addParameter("userID", userID)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM users WHERE ID = :userID")
                .addParameter("userID", userID)
                .executeUpdate();
    }
}
