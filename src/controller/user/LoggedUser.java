
package controller.user;

import java.util.List;
import java.util.TreeSet;

import model.permissions.ConferencePermission;
import model.permissions.PermissionLevel;
import model.permissions.PermissionLevelComparator;
import model.users.User;

/**
 * This class represents the currently logged in user.
 * 
 * @author Tim Mikeladze
 * @version 11-17-2013
 */
public class LoggedUser {
    
    /**
     * The logged in user.
     */
    private static LoggedUser loggedUser;
    
    /**
     * The current user.
     */
    private User user;
    
    /**
     * Tree set for holding the permissions of the user, automatically sorts.
     */
    private TreeSet<PermissionLevel> permissions;
    
    /**
     * Logs a user in and gets their permissions.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A user is logged in and their permission have been retrieved.
     */
    private LoggedUser() {
        permissions = new TreeSet<PermissionLevel>(new PermissionLevelComparator());
    }
    
    /**
     * Gets the single instance of LoggedUser.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The instance of the user is returned.
     * @return single instance of LoggedUser
     */
    public static LoggedUser getInstance() {
        if (loggedUser == null) {
            loggedUser = new LoggedUser();
            return loggedUser;
        }
        return loggedUser;
    }
    
    /**
     * Sets the user object, this needs be called when logging in.
     * 
     * <dt><b>Precondition:</b><dd> requires user != null; <br>
     *                              requires This is called at login.
     * <dt><b>Postcondition:</b><dd> ensures The user object is set.
     * @param user the new user
     */
    public void setUser(final User user) {
        this.user = user;
    }
    
    /**
     * Gets the user.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The user object is returned.
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Gets the permissions set, the permission set holds the user's current permission levels.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The users permissions are returned.
     * @return the permissions set
     */
    public TreeSet<PermissionLevel> getPermissions() {
        return permissions;
    }
    
    /**
     * Gets the highest permission.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The users highest permission is returned.
     * @return the highest permission
     */
    public PermissionLevel getHighestPermission() {
        return permissions.last();
    }
    
    /**
     * Gets the lowest permission.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The users lowest permission is returned.
     * @return the lowest permission
     */
    public PermissionLevel getLowestPermission() {
        return permissions.first();
    }
    
    /**
     * Sets the users Conference permissions.
     * 
     * <dt><b>Precondition:</b><dd> requires conferencePermissionsList != null;
     * <dt><b>Postcondition:</b><dd> ensures The users permissions are set.
     * @param conferencePermissionsList The users conference permissions
     */
    public void setPermissions(final List<ConferencePermission> conferencePermissionsList) {
        for (ConferencePermission conferencePermission : conferencePermissionsList) {
            if (conferencePermission.getPermissionID() == PermissionLevel.AUTHOR.getPermission()) {
                permissions.add(PermissionLevel.AUTHOR);
            }
            if (conferencePermission.getPermissionID() == PermissionLevel.REVIEWER.getPermission()) {
                permissions.add(PermissionLevel.REVIEWER);
            }
            if (conferencePermission.getPermissionID() == PermissionLevel.SUBPROGRAM_CHAIR.getPermission()) {
                permissions.add(PermissionLevel.SUBPROGRAM_CHAIR);
            }
            if (conferencePermission.getPermissionID() == PermissionLevel.PROGRAM_CHAIR.getPermission()) {
                permissions.add(PermissionLevel.PROGRAM_CHAIR);
            }
        }
    }
    
    /**
     * This method "logs out" the user. It resets the user and permissions.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The user is logged out and the permissions are reset.
     */
    public void logout() {
        user = null;
        clearPermissions();
        permissions = new TreeSet<PermissionLevel>(new PermissionLevelComparator());
        
    }
    
    /**
     * Clears the users permissions.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The users permissions are cleared.
     */
    public void clearPermissions() {
        permissions.clear();
    }
}
