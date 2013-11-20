
package model.conferences;

import java.util.List;

import model.database.Database;
import model.users.User;

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
    public int conferenceID;
    
    /**
     * The conference users id.
     */
    public int userID;
    
    /**
     * The users permission id.
     */
    public int permissionID;
    
    /**
     * The conference user.
     */
    public User user;
    
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
                        "SELECT ConferenceID, UserID, PermissionID FROM conference_users WHERE ConferenceID = :conferenceID AND UserID = :userID")
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
    
    /**
     * Gets the user.
     * 
     * @return the user
     */
    public User getUser() {
        return user == null ? User.userFromID(getUserID()) : user;
    }
    
    /**
     * ConferenceUser's toString.
     * 
     * @return Relevant information about a ConferenceUser
     */
    @Override
    public String toString() {
        return "ConferenceUser [getConferenceID()=" + getConferenceID() + ", getUserID()="
                + getUserID() + ", getPermissionID()=" + getPermissionID() + ", getUser()="
                + getUser() + "]";
    }
    
}
