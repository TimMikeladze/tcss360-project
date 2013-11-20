package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.database.Database;
import model.database.DatabaseException;
import model.login.Login;


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
		.createQuery("INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
		.addParameter("firstName", firstName1)
		.addParameter("lastName", lastName1)
		.addParameter("email", email1).executeUpdate();
	}
	
	/*
	 * Tests the isRegistered() method of Login class.
	 */
	@Test
	public void testIsRegistered() {
		// Tests the case where the user should not be in the database.
		assertFalse("User not in the database test for isRegistered()", Login.isRegistered("helloworld@gmail.com"));
		
		
		// Tests the case where the user should be in the database
		assertTrue("User is in the database test for isRegistered()", Login.isRegistered(email1));
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
		} catch (SQLException e) {
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
		} catch (DatabaseException e) {
			fail("registerUser threw an exception when it shouldn't have");
			e.printStackTrace();
		}
		
		assertTrue(Database.hasResults(Database
				.getInstance()
				.createQuery(
						"SELECT 1 FROM users WHERE email = :email")
				.addParameter("email", email2)
				.executeAndFetchTable()));
		
	}

	/**
	 * Tests the loginUser() method of Login class.
	 */
	@Test
	public void testLoginUser() {
		fail("Not yet implemented");
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
