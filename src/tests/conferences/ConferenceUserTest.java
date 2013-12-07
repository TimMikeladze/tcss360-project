
package tests.conferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import model.conferences.ConferenceUser;
import model.database.Database;
import model.permissions.PermissionLevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ConferenceUser class from model.conferences
 * 
 * @author Jordan Matthews
 * @version December 4, 2013
 *
 */
public class ConferenceUserTest {
    
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
     * The Conference user object
     */
    private ConferenceUser user;
    /**
     * The second Conference user object
     */
    private ConferenceUser user2;
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
     * This sets up the Test class for testing of ConferenceUser
     */
    @Before
    public void setUp() {
        // add user
        firstName = "Jon";
        lastName = "Snow";
        email = "youknownothingjonsnow@gmail.com";
        
        // Add the user 'Jon Snow' to the database
        userID = Database.getInstance()
                .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                .addParameter("firstName", firstName).addParameter("lastName", lastName).addParameter("email", email)
                .executeUpdate().getKey(Integer.class);
        
        name = "Conference1";
        location = "Seattle";
        conferenceID = Database.getInstance()
                .createQuery("INSERT INTO conferences (Name, Location, Date) VALUES (:name, :location, :date)")
                .addParameter("name", name).addParameter("location", location).addParameter("date", new Date())
                .executeUpdate().getKey(Integer.class);
        Database.getInstance()
                .createQuery(
                        "INSERT INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:id, :userID, :permissionID)")
                .addParameter("id", conferenceID).addParameter("userID", userID)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission()).executeUpdate();
        user = ConferenceUser.userFromID(conferenceID, userID);
    }
    
    /**
     * Tests the userFromID method
     */
    @Test
    public void testUserFromID() {
        assertEquals("Not the same user", user.getFirstname() + " " + user.getLastname(), "Jon Snow");
    }
    
    /**
     * Test the userFromID method if user does not exist
     */
    @Test
    public void testUserFromIDUserDoesNotExist() {
        user2 = ConferenceUser.userFromID(conferenceID, Integer.MAX_VALUE);
        assertEquals("The user is there", user2, null);
    }
    
    /**
     * Tests the getConferenceID method
     */
    @Test
    public void testGetConferenceID() {
        assertEquals("The ids are not correct", user.getConferenceID(), conferenceID);
    }
    
    /**
     * Tests the getUserID method
     */
    @Test
    public void testUserID() {
        assertEquals("The ids are not correct", user.getUserID(), userID);
    }
    
    /**
     * Tests the getRole method
     */
    @Test
    public void testGetRole() {
        assertEquals("The permissions are not correct", user.getRole(), "Program Chair");
    }
    
    /**
     * Tests the getUsername method
     */
    @Test
    public void testGetUserName() {
        assertEquals("Not the same user", user.getUsername(), "Jon Snow");
    }
    
    /**
     * Tests the getAssignedAsSubProgramChair() method
     */
    @Test
    public void testGetAssignedAsSubProgramChair() {
        assertTrue("SubProgram Chair not assigned", user.getAssignedAsSubProgramChair() == 0);
    }
    
    /**
     * Cleans up the database for when the tests are done
     */
    @After
    public void takeDown() {
        Database.getInstance().createQuery("DELETE FROM conferences WHERE ID = :conferenceID")
                .addParameter("conferenceID", conferenceID).executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
                .addParameter("conferenceID", conferenceID).addParameter("userID", userID).executeUpdate();
        Database.getInstance().createQuery("DELETE FROM users WHERE ID = :userID").addParameter("userID", userID)
                .executeUpdate();
    }
}
