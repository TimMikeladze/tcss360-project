
package tests.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import model.database.Database;
import model.database.DatabaseException;
import model.login.Login;
import model.users.User;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LoginTest {
    
    /**
     * User's first name for testing.
     */
    private static String firstName1;
    
    /**
     * User's last name for testing.
     */
    private static String lastName1;
    
    /**
     * User's email for testing.
     */
    private static String email1;
    
    /**
     * Second user's first name for testing.
     */
    private static String firstName2;
    
    /**
     * Second user's last name for testing.
     */
    private static String lastName2;
    
    /**
     * Second user's email for testing.
     */
    private static String email2;
    
    /**
     * Initializes the tests.
     */
    @BeforeClass
    public static void testSetup() {
        firstName1 = "Jon";
        lastName1 = "Snow";
        email1 = "youknownothingjonsnow@gmail.com";
        
        firstName2 = "King";
        lastName2 = "North";
        email2 = "kingofthenorth@gmail.com";
        
        // Add the user 'Jon Snow' to the database
        Database.getInstance()
                .createQuery(
                        "INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                .addParameter("firstName", firstName1).addParameter("lastName", lastName1)
                .addParameter("email", email1).executeUpdate();
    }
    
    /*
     * Tests the isRegistered() method of Login class.
     */
    @Test
    public void testIsRegistered() {
        // Tests the case where the user should not be in the database.
        assertFalse("User not in the database test for isRegistered()",
                Login.isRegistered("helloworld@gmail.com"));
        
        // Tests the case where the user should be in the database
        assertTrue("User is in the database test for isRegistered()",
                Login.isRegistered(email1));
    }
    
    /**
     * Tests the registerUser() method of Login class in the case where the user
     * is already registered.
     */
    @Test
    public void testRegisterUserException() {
        try {
            // This user was already added to the database during setup so we should expect
            // an exception
            Login.registerUser(firstName1, lastName1, email1);
            fail("Should have thrown an SQLException");
        }
        catch (SQLException e) {
            assertTrue(true);
        }
    }
    
    /**
     * Tests the registerUser() method of Login class.
     */
    @Test
    public void testRegisterUser() {
        try {
            Login.registerUser(firstName2, lastName2, email2);
        }
        catch (DatabaseException e) {
            fail("registerUser threw an exception when it shouldn't have");
            e.printStackTrace();
        }
        
        assertTrue(Database.hasResults(Database.getInstance()
                .createQuery("SELECT 1 FROM users WHERE email = :email")
                .addParameter("email", email2).executeAndFetchTable()));
        
    }
    
    /**
     * Tests the loginUser() method of Login class.
     */
    @Test
    public void testLoginUser() {
        User user = null;
        try {
            user = Login.loginUser(email1);
        }
        catch (DatabaseException e) {
            fail("Unsuccessful login test: The user does not exist");
            e.printStackTrace();
        }
        
        // Check if the right user is logged in
        assertEquals("Match attempt for first name", firstName1, user.getFirstName());
        assertEquals("Match attempt for last name", lastName1, user.getLastName());
        assertEquals("Match attempt for email", email1, user.getEmail());
    }
    
    /**
     * Cleans up temporary data that was used in the tests.
     */
    @AfterClass
    public static void testCleanup() {
        // Remove Jon Snow from database
        Database.getInstance().createQuery("DELETE FROM users WHERE Email = :email")
                .addParameter("email", email1).executeUpdate();
        // Remove King North from database
        Database.getInstance().createQuery("DELETE FROM users WHERE Email = :email")
                .addParameter("email", email2).executeUpdate();
    }
}
