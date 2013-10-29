package model.login;

import java.sql.SQLException;

import model.database.Database;

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
		return Database.getInstance()
				.createQuery("SELECT 1 FROM users WHERE Email = :email")
				.addParameter("email", email).executeAndFetchTable().rows()
				.size() > 0;
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
	public static void registerUser(String firstName, String lastName,
			String email) throws SQLException {
		if (!isRegistered(email)) {
			Database.getInstance()
					.createQuery(
							"INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
					.addParameter("firstName", firstName)
					.addParameter("lastName", lastName)
					.addParameter("email", email).executeUpdate();
		} else {
			throw new SQLException("This email already exists in the database");
		}
	}
}
