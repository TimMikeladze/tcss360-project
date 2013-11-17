/*
 * Tim Mikeladze
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 3, 2013
 */

package model.conferences;

import java.sql.Date;
import java.util.List;

import model.database.Database;
import model.database.DatabaseException;
import model.database.Errors;
import model.permissions.Permission;
import model.permissions.PermissionLevel;

/**
 * The Class ConferenceManager.
 * 
 * @author Tim Mikeladze
 * @author Mohammad Juma
 * @version 11-11-2013
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
				.addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR)
				.executeUpdate();
		return id;
	}

	/**
	 * Removes a conference from the database.
	 * 
	 * @param conferenceID
	 *            The id of the conference to remove.
	 */
	public static void removeConference(int conferenceID) {
		Database.getInstance()
				.createQuery("DELETE FROM conferences WHERE ID = :conferenceID")
				.addParameter("conferenceID", conferenceID).executeUpdate();
		Database.getInstance()
				.createQuery(
						"DELETE FROM conference_users WHERE ID = :conferenceID")
				.addParameter("conferenceID", conferenceID).executeUpdate();
	}

	/**
	 * Adds a user to the conference.
	 * 
	 * @param conferenceID
	 *            The id of the conference to add the user to.
	 * @throws DatabaseException
	 */
	public static void addUserToConference(int conferenceID, int userID,
			PermissionLevel permissionID) throws DatabaseException {
		if (conferenceExists(conferenceID)) {
			Database.getInstance()
					.createQuery(
							"INSERT IGNORE INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:conferenceID, :userID, :permissionID)")
					.addParameter("conferenceID", conferenceID)
					.addParameter("userID", userID)
					.addParameter("permissionID", permissionID.getPermission())
					.executeUpdate();
		} else {
			throw new DatabaseException(Errors.CONFERENCE_DOES_NOT_EXIST);
		}
	}

	/**
	 * Removes a user from the conference.
	 * 
	 * @param conferenceID
	 *            The id of the conference to remove the user from.
	 */
	public static void removeUserFromConference(int conferenceID, int userID) {
		Database.getInstance()
				.createQuery(
						"DELETE FROM conference_users WHERE UserID = :userID AND ConferenceID = :conferenceID")
				.addParameter("userID", userID)
				.addParameter("conferenceID", conferenceID).executeUpdate();
	}

	/**
	 * Checks if conference exists
	 * 
	 * @param conferenceID
	 *            conference id to check
	 * @return returns true if conference exists
	 */
	public static boolean conferenceExists(int conferenceID) {
		return Database.hasResults(Database
				.getInstance()
				.createQuery(
						"SELECT 1 FROM conferences WHERE ID = :conferenceID")
				.addParameter("conferenceID", conferenceID)
				.executeAndFetchTable());
	}

	/**
	 * Gets a list of users in a conference represented as ConferenceUser
	 * objects
	 * 
	 * @param id
	 *            The id of the conference get users for
	 * @return the list of users
	 */
	// TODO change from a list to a map
	@Permission(level = 400)
	public static List<ConferenceUser> getUsersInConference(int id) {
		return Database
				.getInstance()
				.createQuery(
						"SELECT ConferenceID, UserID, PermissionID FROM conference_users WHERE ConferenceID = :id")
				.addParameter("id", id).executeAndFetch(ConferenceUser.class);
	}
}
