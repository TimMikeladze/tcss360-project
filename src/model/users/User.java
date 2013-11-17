package model.users;

import java.util.List;

import model.database.Database;

/**
 * The Class User.
 */
public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String email;

	/**
	 * Create a user object given an email
	 * 
	 * @param email
	 *            the user's email
	 * @return the User object, null if user with given email does not exist
	 */
	public static User userFromEmail(String email) {
		User user = null;
		List<User> results = Database
				.getInstance()
				.createQuery(
						"SELECT ID, Firstname, Lastname, Email FROM users WHERE Email = :email")
				.addParameter("email", email).executeAndFetch(User.class);
		if (Database.hasResults(results)) {
			user = results.get(0);
		}
		return user;
	}

	/**
	 * Create a user object given an id
	 * 
	 * @param email
	 *            the user's id
	 * @return the User object, null if user with given id does not exist
	 */
	public static User userFromID(int id) {
		User user = null;
		List<User> results = Database
				.getInstance()
				.createQuery(
						"SELECT ID, Firstname, Lastname, Email FROM users WHERE ID = :id")
				.addParameter("id", id).executeAndFetch(User.class);
		if (Database.hasResults(results)) {
			user = results.get(0);
		}
		return user;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getID() {
		return id;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + "]";
	}
}
