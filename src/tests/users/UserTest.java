package tests.users;

import static org.junit.Assert.*;

import model.database.Database;
import model.users.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Conference Manager Class from tests.conferences
 * 
 * @author Mohammad Juma
 * @version December 5, 2013
 */
public class UserTest {
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
        
    }
    
    /**
     * Tests the userFromEmail Method
     */
    @Test
    public void testUserFromEmail() {
        assertEquals("User created", User.userFromEmail(email).getEmail(), email);
        assertNull("User not found", User.userFromEmail("notARealEmail@test.com"));
    }
    
    /**
     * Tests the userFromID method
     */
    @Test
    public void testUserFromID() {
        assertEquals("User created", User.userFromID(userID).getFirstName(), firstName);
        assertNull("User not found", User.userFromID(Integer.MAX_VALUE));
    }
    
    /**
     * Tests the getters
     */
    @Test
    public void testGetters() {
        assertEquals("User created", User.userFromID(userID).getFullName(), firstName + " " + lastName);
        assertEquals("User created", User.userFromID(userID).getID(), userID);
        assertEquals("User created", User.userFromID(userID).getLastName(), lastName);
        assertTrue("User string", User.userFromID(userID).toString() != null);
    }
    
    /**
     * Cleans up the class after testing
     */
    @After
    public void takeDown() {
        Database.getInstance()
            .createQuery("DELETE FROM users WHERE ID = :userID")
            .addParameter("userID", userID)
            .executeUpdate();
    }
}
