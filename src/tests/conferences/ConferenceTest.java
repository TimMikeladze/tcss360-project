package tests.conferences;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.conferences.Conference;
import model.database.Database;
import model.permissions.PermissionLevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Conference class in the model.conferences package
 * 
 * @author Jordan Matthews
 * @version December 3, 2013
 */
public class ConferenceTest {
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
     * The conference ID
     */
    private int conferenceID2;
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
     * Initializes database and objects for testing
     */
    @Before
    public void initializeTests() {
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
        conferenceID2 = Database.getInstance()
                .createQuery("INSERT INTO conferences (Name, Location, Date) VALUES (:name, :location, :date)")
                .addParameter("name", name)
                .addParameter("location", location)
                .addParameter("date", new Date())
                .executeUpdate()
                .getKey(Integer.class);
        Database.getInstance()
                .createQuery("INSERT INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:id, :userID, :permissionID)")
                .addParameter("id", conferenceID2)
                .addParameter("userID", userID)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission())
                .executeUpdate();
    }
    
    /**
     * Tests that the conference IDs are correct
     */
    @Test
    public void testConferenceFromID() {
        assertEquals("Conference has been created successfully", Conference.conferenceFromID(conferenceID).getId(), conferenceID);
    }
    
    /**
     * Tests that the conference will be null if the incorrect ID is passed
     */
    @Test
    public void testConferenceFromIDNullID() {
        assertEquals("The conference was not found", Conference.conferenceFromID(conferenceID2 + 1), null);
    }
    
    /**
     * Tests that the correct Program chair name is used
     */
    @Test
    public void testProgramChairName() {
        assertEquals("Conference program chair name is correct", Conference.conferenceFromID(conferenceID).getProgramChair(), firstName + " " + lastName);
    }
    
    /**
     * Tests that the correct program chair ID returned
     */
    @Test
    public void testProgramChairID() {
        assertEquals("Conference program chair ID is correct", Conference.conferenceFromID(conferenceID).getProgramChairId(), userID);
    }
    
    /**
     * Tests the correct conference name
     */
    @Test
    public void testConferenceName() {
        assertEquals("Conference name is correct", Conference.conferenceFromID(conferenceID).getName(), name);
    }
    
    /**
     * Tests that the correct location is returned
     */
    @Test
    public void testConferenceLocation() {
        assertEquals("Conference location is correct", Conference.conferenceFromID(conferenceID).getLocation(), location);
    }
    
    /**
     * Tests that the correct conference date is returned
     */
    @Test
    public void testConferenceDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("Conference date is correct", Conference.conferenceFromID(conferenceID).getDate(), sdf.format(date));
    }
    
    /**
     * Tests that the correct number of authors is returned
     */
    @Test
    public void testConferenceNumberOfAuthors() {
        assertEquals("Conference number of authors is correct", Conference.conferenceFromID(conferenceID).getNumberOfAuthors(), 0);
    }
    
    /**
     * Tests that the correct number of reviewers is returned
     */
    @Test
    public void testConferenceNumberOfReviewers() {
        assertEquals("Conference number of reviewers is correct", Conference.conferenceFromID(conferenceID).getNumberOfReviewers(), 0);
    }
    
    /**
     * Tests the hashCode method
     */
    @Test
    public void testHashCode() {
        assertEquals("Hash Code is correct", Conference.conferenceFromID(conferenceID).hashCode(), conferenceID + 31);
    }
    
    /**
     * Tests the compareTo method
     */
    @Test
    public void testCompareTo() {
        Conference conf = Conference.conferenceFromID(conferenceID);
        Conference conf2 = Conference.conferenceFromID(conferenceID2);
        assertEquals("Compare is equal", Math.abs(conf.compareTo(conf2)), Math.abs(conf2.compareTo(conf)));
    }
    
    /**
     * Tests the equals method
     */
    @Test
    public void testEquals() {
        Conference conf = Conference.conferenceFromID(conferenceID);
        Conference conf2 = Conference.conferenceFromID(conferenceID);
        Conference conf3 = Conference.conferenceFromID(conferenceID2);
        assertTrue("Conferences are equal", conf.equals(conf2));
        assertFalse("Conferences are not equal", conf.equals(null));
        assertFalse("Conferences are not equal", conf2.equals(conf3));
        assertTrue("Conferences are equal", conf.equals(conf));
        assertFalse("Conferences are not equal", conf.equals(new Date()));
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
                .createQuery("DELETE FROM conferences WHERE ID = :conferenceID")
                .addParameter("conferenceID", conferenceID2)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
                .addParameter("conferenceID", conferenceID2)
                .addParameter("userID", userID)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM users WHERE ID = :userID")
                .addParameter("userID", userID)
                .executeUpdate();
    }
}
