package model.conferences;

import java.sql.Date;
import java.util.List;

import model.database.Database;
import model.users.User;

/**
 * The Class Conference.
 */
public class Conference {
	private int id;
	private String name;
	private String location;
	private Date date;
	private int programChairID;
	private User programChair;

	/**
	 * Create a conference object given and ID
	 * 
	 * @param id
	 *            the id of the conference
	 * @return conference or null if not exists
	 */
	public static Conference conferenceFromID(int id) {
		Conference conference = null;
		List<Conference> results = Database
				.getInstance()
				.createQuery(
						"SELECT ID, Name, Location, Date, ProgramChairID FROM users WHERE ID = :id")
				.addParameter("id", id).executeAndFetch(Conference.class);
		if (Database.hasResults(results)) {
			conference = results.get(0);
		}
		return conference;
	}

	/**
	 * Get the User object for the program chair
	 * 
	 * @return the program chair's User object
	 */
	public User getProgramChair() {
		if (programChair == null) {
			programChair = User.userFromID(programChairID);
		}
		return programChair;
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
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the location.
	 * 
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the program chair id.
	 * 
	 * @return the program chair id
	 */
	public int getProgramChairID() {
		return programChairID;
	}

	@Override
	public String toString() {
		return "Conference [id=" + id + ", name=" + name + ", location="
				+ location + ", date=" + date + ", programChairID="
				+ programChairID + ", programChair=" + programChair + "]";
	}
}
