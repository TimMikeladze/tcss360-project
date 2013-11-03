package model.conferences;

import java.sql.Date;

import model.database.Database;

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
		return Database
				.getInstance()
				.createQuery(
						"INSERT INTO conferences (Name, Location, Date, ProgramChairID) VALUES (:name, :location, :date, :programChairID)")
				.addParameter("name", name).addParameter("location", location)
				.addParameter("date", date)
				.addParameter("programChairID", programChairID).executeUpdate()
				.getKey(Integer.class);
	}

}
