
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
     * The id of the Conference.
     */
    private int conferenceID;
    
    /**
     * The Conference Users id.
     */
    private int userID;
    
    /**
     * The Conference Users full name.
     */
    private String username;
    
    /**
     * The Conference Users permission id.
     */
    private int permissionID;
    
    /**
     * The Conference Users first name.
     */
    private String firstName;
    
    /**
     * The Conference Users last name.
     */
    private String lastName;
    
    /**
     * The number of Papers the Conference User is a Subprogram Chair of.
     */
    private int assignedAsSubProgramChair;
    
    /**
     * Create a Conference User object given the Conference id and user id.
     * 
     * <dt><b>Precondition:</b>
     * <dd>requires conferenceID > 0 and is valid. <br>
     * requires userID > 0 and is valid.
     * <dt><b>Postcondition:</b>
     * <dd>ensures A ConferenceUser object is returned.
     * 
     * @param conferenceID The conferences id
     * @param userID The users id
     * @return the ConferenceUser object if found, else returns null
     */
    public static ConferenceUser userFromID(final int conferenceID, final int userID) {
        ConferenceUser user = null;
        List<ConferenceUser> results = Database.getInstance()
                                               .createQuery(
                                                       "SELECT cu.ConferenceID, cu.UserID, CONCAT(u.Firstname, ' ', u.Lastname) AS Username, u.Firstname, u.Lastname, cu.PermissionID, (SELECT COUNT(1) FROM assigned_papers AS a WHERE a.UserID = cu.UserID AND PermissionID = 300) AS AssignedAsSubProgramChair FROM conference_users AS cu JOIN users AS u ON u.ID = cu.UserID WHERE cu.ConferenceID = :conferenceID AND cu.UserID = :userID ORDER BY cu.PermissionID DESC LIMIT 1")
                                               .addParameter("conferenceID", conferenceID)
                                               .addParameter("userID", userID)
                                               .executeAndFetch(ConferenceUser.class);
        if (Database.hasResults(results)) {
            user = results.get(0);
        }
        return user;
    }
    
    /**
     * Gets the Conferences id.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The Conferences id is returned.
     * 
     * @return he Conferences id
     */
    public int getConferenceID() {
        return conferenceID;
    }
    
    /**
     * Gets the Conference Users id.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The Conferences Users id is returned.
     * 
     * @return the Conference Users id
     */
    public int getUserID() {
        return userID;
    }
    
    /**
     * Gets the Conference Users role.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The Conferences Users role is returned.
     * 
     * @return the Conference Users role
     */
    public String getRole() {
        String role = "";
        for (PermissionLevel permissionLevel : PermissionLevel.values()) {
            if (permissionLevel.getPermission() == permissionID) {
                role = PermissionLevel.getRole(permissionLevel);
                break;
            }
        }
        return role;
    }
    
    /**
     * Gets the Conference Users full name.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The Conferences Users full name is returned.
     * 
     * @return the Conference Users full name.
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Gets the Conference Users first name.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The Conferences Users first name is returned.
     * 
     * @return the Conference Users first name
     */
    public String getFirstname() {
        return firstName;
    }
    
    /**
     * Gets the Conference Users last name.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The Conferences Users last name is returned.
     * 
     * @return the Conference Users last name.
     */
    public String getLastname() {
        return lastName;
    }
    
    /**
     * Gets the number of Papers the Conference User is assigned to as a Subprogram Chair.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The number of Papers the Conference User is assigned to as a Subprogram Chair
     * is returned.
     * 
     * @return the number of Papers the Conference User is assigned to as a Subprogram Chair
     */
    public int getAssignedAsSubProgramChair() {
        return assignedAsSubProgramChair;
    }
}
