
package model.conferences;

import java.util.List;

import model.database.Database;

/**
 * This class holds all the information for a conference user.
 *
 * @author Tim Mikeladze
 * @version 11-03-2013
 */
public class ConferenceUser {

    /**
     * The conference id.
     */
    private int conferenceID;

    /**
     * The conference users id.
     */
    private int userID;

    /**
     * The username
     */
    private String username;

    /**
     * The users permission id.
     */
    public int permissionID;



    /**
     * Create a conference user object given the conference id and user id.
     *
     * @param conferenceID The conferences id
     * @param userID The users id
     * @return the ConferenceUser object if found, else returns null
     */
    public static ConferenceUser userFromID(final int conferenceID, final int userID) {
        ConferenceUser user = null;
        List<ConferenceUser> results = Database
                .getInstance()
                .createQuery(
                        "SELECT cu.ConferenceID, cu.UserID, CONCAT(u.Firstname, ' ', u.Lastname) AS Username, cu.PermissionID FROM conference_users AS cu JOIN users AS u ON u.ID = cu.UserID WHERE cu.ConferenceID = :conferenceID AND cu.UserID = :userID ORDER BY cu.PermissionID DESC LIMIT 1")
                .addParameter("conferenceID", conferenceID).addParameter("userID", userID)
                .executeAndFetch(ConferenceUser.class);
        if (Database.hasResults(results)) {
            user = results.get(0);
        }
        return user;
    }

    /**
     * Gets the conference id.
     *
     * @return the conference id
     */
    public int getConferenceID() {
        return conferenceID;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the permission id.
     *
     * @return the permission id
     */
    public int getPermissionID() {
        return permissionID;
    }

    @Override
    public String toString() {
        return username;
    }
}
