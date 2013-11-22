
package model.conferences;

import java.sql.Timestamp;
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
 * @version 11-03-2013
 */
public class ConferenceManager {
    
    /**
     * Create a conference.
     * 
     * @param name the name of the conference
     * @param location the location of the conference
     * @param date the date the conference is to take place
     * @return returns the ID of the created conference
     */
    public static int createConference(final String name, final String location,
            final Timestamp date, final int programChairID) {
        String sqlDate = date.toString();
        int id = Database.getInstance()
                         .createQuery(
                                 "INSERT INTO conferences (Name, Location, Date) VALUES (:name, :location, :date)")
                         .addParameter("name", name)
                         .addParameter("location", location)
                         .addParameter("date", sqlDate)
                         .executeUpdate()
                         .getKey(Integer.class);
        Database.getInstance()
                .createQuery(
                        "INSERT INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:id, :userID, :permissionID)")
                .addParameter("id", id)
                .addParameter("userID", programChairID)
                .addParameter("permissionID", PermissionLevel.PROGRAM_CHAIR.getPermission())
                .executeUpdate();
        return id;
    }
    
    /**
     * Removes a conference from the database.
     * 
     * @param conferenceID The id of the conference to remove.
     */
    @Permission(level = 400)
    public static void removeConference(final int conferenceID) {
        Database.getInstance()
                .createQuery("DELETE FROM conferences WHERE ID = :conferenceID")
                .addParameter("conferenceID", conferenceID)
                .executeUpdate();
        Database.getInstance()
                .createQuery("DELETE FROM conference_users WHERE ID = :conferenceID")
                .addParameter("conferenceID", conferenceID)
                .executeUpdate();
    }
    
    /**
     * Adds a user to the conference.
     * 
     * @param conferenceID The id of the conference to add the user to
     * @param userID The id of the user being added
     * @param permissionID The permission of the user being added
     * @throws DatabaseException
     */
    @Permission(level = 400)
    public static void addUserToConference(final int conferenceID, final int userID,
            final PermissionLevel permissionID) throws DatabaseException {
        if (conferenceExists(conferenceID)) {
            Database.getInstance()
                    .createQuery(
                            "INSERT IGNORE INTO conference_users (ConferenceID, UserID, PermissionID) VALUES (:conferenceID, :userID, :permissionID)")
                    .addParameter("conferenceID", conferenceID)
                    .addParameter("userID", userID)
                    .addParameter("permissionID", permissionID.getPermission())
                    .executeUpdate();
        }
        else {
            throw new DatabaseException(Errors.CONFERENCE_DOES_NOT_EXIST);
        }
    }
    
    /**
     * Removes a user from the conference.
     * 
     * @param conferenceID The id of the conference to remove the user from
     * @param userID The id of the user being removed
     */
    @Permission(level = 400)
    public static void removeUserFromConference(final int conferenceID, final int userID) {
        Database.getInstance()
                .createQuery(
                        "DELETE FROM conference_users WHERE UserID = :userID AND ConferenceID = :conferenceID")
                .addParameter("userID", userID)
                .addParameter("conferenceID", conferenceID)
                .executeUpdate();
    }
    
    /**
     * Checks if a given conference exists.
     * 
     * @param conferenceID conference id to check
     * @return returns true if conference exists
     */
    public static boolean conferenceExists(final int conferenceID) {
        return Database.hasResults(Database.getInstance()
                                           .createQuery(
                                                   "SELECT 1 FROM conferences WHERE ID = :conferenceID")
                                           .addParameter("conferenceID", conferenceID)
                                           .executeAndFetchTable());
    }
    
    /**
     * Gets a list of users in a conference represented as ConferenceUser objects.
     * 
     * @param id The id of the conference get users for
     * @return the list of users
     */
    @Permission(level = 300)
    public static List<ConferenceUser> getUsersInConference(final int id) {
        return Database.getInstance()
                       .createQuery(
                               "SELECT ConferenceID, UserID, PermissionID FROM conference_users WHERE ConferenceID = :id")
                       .addParameter("id", id)
                       .executeAndFetch(ConferenceUser.class);
    }
    
    /**
     * Get a list of conferences represented by Conference objects.
     * 
     * @return returns List<Conference>
     */
    public static List<Conference> getConferences() {
        return Database.getInstance()
                       .createQuery(
                               "SELECT c.ID, c.Name, c.Location, c.Date, c.ProgramChairID,"
                                       + "(SELECT COUNT(1) FROM conference_users AS cu WHERE cu.ConferenceID = c.ID AND cu.PermissionID = 100) AS Reviewers,"
                                       + "(SELECT COUNT(1) FROM conference_users AS cu WHERE cu.ConferenceID = c.ID AND cu.PermissionID = 200) AS Authors "
                                       + "FROM conferences AS c ORDER BY c.Date DESC")
                       .executeAndFetch(Conference.class);
    }
}
