
package model.conferences;

import java.util.List;

import model.database.Database;
import model.permissions.PermissionLevel;

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
    private int permissionID;
    
    private String firstName;
    private String lastName;
    
    /**
     * Create a conference user object given the conference id and user id.
     * 
     * @param conferenceID The conferences id
     * @param userID The users id
     * @return the ConferenceUser object if found, else returns null
     */
    public static ConferenceUser userFromID(final int conferenceID, final int userID) {
        ConferenceUser user = null;
        List<ConferenceUser> results = Database.getInstance()
                                               .createQuery(
                                                       "SELECT cu.ConferenceID, cu.UserID, CONCAT(u.Firstname, ' ', u.Lastname) AS Username, u.Firstname, u.Lastname, cu.PermissionID FROM conference_users AS cu JOIN users AS u ON u.ID = cu.UserID WHERE cu.ConferenceID = :conferenceID AND cu.UserID = :userID ORDER BY cu.PermissionID DESC LIMIT 1")
                                               .addParameter("conferenceID", conferenceID)
                                               .addParameter("userID", userID)
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
    
    public String getRole() {
        String role = "";
        for (PermissionLevel p : PermissionLevel.values()) {
            if (p.getPermission() == permissionID) {
                role = PermissionLevel.getRole(p);
                break;
            }
        }
        return role;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getFirstname() {
        return firstName;
    }
    
    public String getLastname() {
        return lastName;
    }
}
