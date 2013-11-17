package model.conferences;

import java.sql.Timestamp;
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
	private Timestamp date;
	private int programChairID;
	private User programChair;
	private int authors;
	private int reviewers;

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
						"SELECT c.ID, c.Name, c.Location, c.Date, c.ProgramChairID,"
								+ "(SELECT COUNT(1) FROM conference_users AS cu WHERE cu.ConferenceID = c.ID AND cu.PermissionID = 100) AS Reviewers,"
								+ "(SELECT COUNT(1) FROM conference_users AS cu WHERE cu.ConferenceID = c.ID AND cu.PermissionID = 200) AS Authors "
								+ "FROM conferences AS c WHERE c.ID = :id ORDER BY c.Date DESC")
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
		return programChair == null
				? User.userFromID(programChairID)
				: programChair;

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
	public Timestamp getDate() {
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

	/**
	 * @return the number of authors
	 */
	public int getAuthors() {
		return authors;
	}

	/**
	 * @return the number of reviewers
	 */
	public int getReviewers() {
		return reviewers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Conference [getProgramChair()=" + getProgramChair()
				+ ", getID()=" + getID() + ", getName()=" + getName()
				+ ", getLocation()=" + getLocation() + ", getDate()="
				+ getDate() + ", getProgramChairID()=" + getProgramChairID()
				+ ", getAuthors()=" + getAuthors() + ", getReviewers()="
				+ getReviewers() + "]";
	}

}
