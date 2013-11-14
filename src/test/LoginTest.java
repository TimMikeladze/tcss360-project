package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import model.database.Database;
import model.login.Login;


public class LoginTest {
	
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
	 * Initializes the tests.
	 */
	@Before
	public void initialize() {
		firstName = "Jon";
		lastName = "Snow";
		email = "youknownothingjonsnow@gmail.com";
		
		// Add the user 'Jon Snow' to the database
		Database.getInstance()
		.createQuery(
				"INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
		.addParameter("firstName", firstName)
		.addParameter("lastName", lastName)
		.addParameter("email", email).executeUpdate();
	}
	
	/*
	 * Tests the isRegistered() method of Login class.
	 */
	@Test
	public void testIsRegistered() {
		// Tests the case where the user should not be in the database.
		assertFalse("User not in the database test for isRegistered()", Login.isRegistered("helloworld@gmail.com"));
		
		
		// Tests the case where the user should be in the database
		assertTrue("User is in the database test for isRegistered()", Login.isRegistered(email));
	}

	/**
	 * Tests the registerUser() method of Login class in the case where the user
	 * is already registered.
	 */
	@Test
	public void testRegisterUserException() {
		try {
			Login.registerUser(firstName, lastName, email);
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
		fail("Not yet implemented");
	}

	@Test
	public void testLoginUser() {
		fail("Not yet implemented");
	}

}
