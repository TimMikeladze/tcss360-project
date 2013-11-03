package model.conferences;

import java.sql.Date;
import java.util.List;

import model.database.Database;
import model.permissions.Permissions;

/**
 * The Class ConferenceManager.
 */
public class ConferenceManager {
	/**
	 * Create a conference
	 * 
	 * @param name
	 *            the name of the conference
	 * @param location
	 *            the location of the conference
	 * @param date
	 *            the date the conference is to take place
	 * @param programChairID
	 *            the ID of program chair, this is the user who creates the
	 *            conference
	 * @return returns the ID of the created conference
	 */
	public static int createConference(String name, String location, Date date,
			int programChairID) {
		// TODO why is there a column for ProgramChairID when that is stored in
		// conference_users. Redundant?
		int id = Database
				.getInstance()
				.createQuery(
						"INSERT INTO conferences (Name, Location, Date, ProgramChairID) VALUES (:name, :location, :date, :programChairID)")
				.addParameter("name", name).addParameter("location", location)
				.addParameter("date", date)
				.addParameter("programChairID", programChairID).executeUpdate()
				.getKey(Integer.class);
		Database.getInstance()
				.createQuery(
						"INSERT INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:id, :userID, :permissionID)")
				.addParameter("id", id).addParameter("userID", programChairID)
				.addParameter("permissionID", Permissions.PROGRAM_CHAIR)
				.executeUpdate();
		return id;
	}

	/**
	 * Gets a list of users in a conference represented as ConferenceUser
	 * objects
	 * 
	 * @param id
	 *            the id of the conference get users for
	 * @return the list of users
	 */
	// TODO change from a list to a map
	public static List<ConferenceUser> getUsersInConference(int id) {
		return Database
				.getInstance()
				.createQuery(
						"SELECT ConferenceID, UserID, PermissionID FROM conference_users WHERE ConferenceID = :id")
				.addParameter("id", id).executeAndFetch(ConferenceUser.class);
	}
}
