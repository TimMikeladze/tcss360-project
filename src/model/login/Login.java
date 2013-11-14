package model.login;

import java.sql.SQLException;

import model.database.Database;
import model.database.Errors;
import model.users.User;

/**
 * Handles user login and registration.
 */
public class Login {

	/**
	 * Checks if user is registered given an email.
	 * 
	 * @param email
	 *            the email to check
	 * @return true if user is registered
	 */
	public static boolean isRegistered(String email) {
		return Database.hasResults(Database.getInstance()
				.createQuery("SELECT 1 FROM users WHERE Email = :email")
				.addParameter("email", email).executeAndFetchTable());
	}

	/**
	 * Register a user.
	 * 
	 * @param firstName
	 *            the user's first name
	 * @param lastName
	 *            the user's last name
	 * @param email
	 *            the user's email
	 * @throws SQLException
	 *             if email already exists in DB
	 */
	public static int registerUser(String firstName, String lastName, String email)
			throws SQLException {
		int id;
		if (!isRegistered(email)) {
			id = Database
					.getInstance()
					.createQuery(
							"INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
					.addParameter("firstName", firstName)
					.addParameter("lastName", lastName)
					.addParameter("email", email).executeUpdate()
					.getKey(Integer.class);
		} else {
			throw new SQLException(Errors.EMAIL_EXISTS);
		}
		return id;
	}

	/**
	 * Logs in a user and returns the corresponding User object
	 * 
	 * @param email
	 *            the user's email
	 * @return the User object or null if user for given email doesn't exist
	 * @throws SQLException
	 *             if email doesn't exist in DB
	 */
	public static User loginUser(String email) throws SQLException {
		User user = null;
		if (isRegistered(email)) {
			user = User.userFromEmail(email);
		} else {
			throw new SQLException(Errors.EMAIL_DOES_NOT_EXIST);
		}
		return user;
	}
}
