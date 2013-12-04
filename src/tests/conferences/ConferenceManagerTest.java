package tests.conferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.data.Table;

import model.conferences.Conference;
import model.conferences.ConferenceManager;
import model.conferences.ConferenceUser;
import model.database.Database;
import model.database.DatabaseException;
import model.database.Errors;
import model.permissions.PermissionLevel;

/**
 * Tests the Conference Manager Class from tests.conferences
 * 
 * @author Jordan Matthews
 * @version November 29, 2013
 */

public class ConferenceManagerTest {
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
    private int userID2;
    private int userID3;
    private int userID4;
    private int userID5;
    private static int id;
    private static String name;
    private static String location;
    private static Timestamp date;
    private static int userID;
    
    /**
     * Setup the class for testing
     */
    @Before
    public void setup() {
        ConferenceManager temp = new ConferenceManager();
        temp.toString();
        name = "Test Conference";
        location = "Test conference location";
        date = new Timestamp(0);
        // add user
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
        
        firstName = "Daenerys";
        lastName = "Targaryen";
        email = "ilikedragons@gmail.com";

        // add user 'Daenerys Targaryen' to the database
        userID2 = Database.getInstance()
                .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                .addParameter("firstName", firstName)
                .addParameter("lastName", lastName)
                .addParameter("email", email)
                .executeUpdate()
                .getKey(Integer.class);
        firstName = "Tyrion";
        lastName = "Lannister";
        email = "ilikemoney@gmail.com";

        // add user 'Tyrion Lannister' to the database
        userID3 = Database.getInstance()
                .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                .addParameter("firstName", firstName)
                .addParameter("lastName", lastName)
                .addParameter("email", email)
                .executeUpdate()
                .getKey(Integer.class);
        
        firstName = "Jorah";
        lastName = "Mormont";
        email = "iprotectthequeen@gmail.com";
        // add user 'Jorah Mormont' to the database
        userID4 = Database.getInstance()
                .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                .addParameter("firstName", firstName)
                .addParameter("lastName", lastName)
                .addParameter("email", email)
                .executeUpdate()
                .getKey(Integer.class);
        
        firstName = "Stannis";
        lastName = "Baratheon";
        email = "lordofdragonstone@gmail.com";
        // add user 'Stannis Baratheon' to the database
        userID5 = Database.getInstance()
                .createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                .addParameter("firstName", firstName)
                .addParameter("lastName", lastName)
                .addParameter("email", email)
                .executeUpdate()
                .getKey(Integer.class);
        id = ConferenceManager.createConference(name, location, date, userID);
    }
    
    @Test
    public void testCreateConference() {
        Table t = Database.getInstance()
                .createQuery("SELECT COUNT(1) FROM conferences WHERE ID = :id")
                .addParameter("id", id)
                .executeAndFetchTable();
        assertTrue("Conference created", Database.hasResults(t));
    }
    
    @Test
    public void testRemoveConference() {
        int id2 = ConferenceManager.createConference(name, location, date, userID2);
        Table t = Database.getInstance()
                .createQuery("SELECT COUNT(1) FROM conferences WHERE ID = :id")
                .addParameter("id", id2)
                .executeAndFetchTable();
        ConferenceManager.removeConference(id2);
        assertFalse("Conference removed", !Database.hasResults(t));
        
    }
    
    @Test
    public void testAddUserToConference() {
        try {
            ConferenceManager.addUserToConference(id, userID2, PermissionLevel.PROGRAM_CHAIR);
            assertTrue("The user is in the conference", ConferenceManager.userInConference(id, userID2, PermissionLevel.PROGRAM_CHAIR));
        } catch (DatabaseException e) {
            fail("User already exists in conference");
        }
    }
    
    @Test
    public void testAddUserToConferenceNoConference() {
        try {
            ConferenceManager.addUserToConference(Integer.MAX_VALUE, userID2, PermissionLevel.PROGRAM_CHAIR);
        } catch (DatabaseException e) {
            assertEquals("The conference doesn't exist", e.getLocalizedMessage(), Errors.CONFERENCE_DOES_NOT_EXIST.toString());
        }
    }
    
    @Test
    public void testAddUserToConferenceUserAlreadyThere() {
        try {
            ConferenceManager.addUserToConference(id, userID2, PermissionLevel.PROGRAM_CHAIR);
            ConferenceManager.addUserToConference(id, userID2, PermissionLevel.PROGRAM_CHAIR);
        } catch (DatabaseException e) {
            assertEquals("The user already exists", e.getLocalizedMessage(), Errors.USER_ALREADY_IN_CONFERENCE.toString());
        }
    }
    
    @Test
    public void testAddReviewerToConference() {
        try {
            ConferenceManager.addReviewerToConference(id, userID3);
            assertTrue("The reviewer is in the conference", ConferenceManager.userInConference(id, userID3, PermissionLevel.REVIEWER));
        } catch (DatabaseException e) {
            fail("Doesn't work");
        }
    }
    
    @Test
    public void testAddSubProgramChairToConference() {
        try {
            ConferenceManager.addReviewerToConference(id, userID4);
            ConferenceManager.addSubProgramChairToConference(id, userID4);
            assertTrue("The reviewer is in the conference", ConferenceManager.userInConference(id, userID4, PermissionLevel.SUBPROGRAM_CHAIR));
        } catch (DatabaseException e) {
            fail("Doesn't work");
        }
    }
    
    @Test
    public void testAddSubProgramChairToConferenceNotReviewer() {
        try {
            ConferenceManager.addSubProgramChairToConference(id, userID4);
        } catch (DatabaseException e) {
            assertEquals("The user is not a reviewer", e.getLocalizedMessage(), Errors.USER_NOT_REVIEWER.toString());
        }
    }
    
    @Test
    public void testRemoveUserFromConference() {
        try {
            ConferenceManager.addUserToConference(id, userID5, PermissionLevel.SUBPROGRAM_CHAIR);
            ConferenceManager.removeUserFromConference(id, userID5);
            assertTrue("The user is gone", !ConferenceManager.userInConference(id, userID5, PermissionLevel.SUBPROGRAM_CHAIR));
        } catch (DatabaseException e) {
            fail("Does not work");
        }
    }
    
    @Test
    public void testGetUsersInConference() {
        try {
            ConferenceManager.removeUserFromConference(id, userID);
            ConferenceManager.removeUserFromConference(id, userID2);
            ConferenceManager.removeUserFromConference(id, userID3);
            ConferenceManager.removeUserFromConference(id, userID4);
            ConferenceManager.addUserToConference(id, userID5, PermissionLevel.SUBPROGRAM_CHAIR);
            List<ConferenceUser> list = ConferenceManager.getUsersInConference(id);
            List<ConferenceUser> list2 = ConferenceManager.getUsersInConference(id, PermissionLevel.SUBPROGRAM_CHAIR);
            assertEquals("The user is in the list", firstName, list.get(0).getFirstname());
            assertEquals("The user is in the list", firstName, list2.get(0).getFirstname());
        } catch (DatabaseException e) {
            fail("Does not work");
        }
    }
    
    @Test
    public void testGetConferences() {
        List<Conference> list = ConferenceManager.getConferences();
        boolean test = false;
        int x = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getID() == id) {
                x = i;
                test = true;
                break;
            }
        }
        if (test) {
            assertEquals("The conference is in the list", list.get(x).getID(), id); 
        } else {
            fail("The id was not found");
        }
    }
    
    @Test
    public void testGetConferencesForUser() {
        List<Conference> list = ConferenceManager.getConferencesForUser(userID);
        assertEquals("The conference is in the list", list.get(0).getID(), id);
    }

    @After
    public void cleanup() {
        Database.getInstance()
            .createQuery("DELETE FROM conferences WHERE ID = :conferenceID")
            .addParameter("conferenceID", id)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
            .addParameter("conferenceID", id)
            .addParameter("userID", userID)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM users WHERE ID = :userID")
            .addParameter("userID", userID)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM users WHERE ID = :userID")
            .addParameter("userID", userID2)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM users WHERE ID = :userID")
            .addParameter("userID", userID3)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM users WHERE ID = :userID")
            .addParameter("userID", userID4)
            .executeUpdate();
        Database.getInstance()
            .createQuery("DELETE FROM users WHERE ID = :userID")
            .addParameter("userID", userID5)
            .executeUpdate();
    }
}