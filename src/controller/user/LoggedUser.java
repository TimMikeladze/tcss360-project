
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
     * Tree set used holding the permissions of the user, automatically sorts.
     */
    private TreeSet<PermissionLevel> permissions;
    
    /**
     * Active conference id
     */
    private int activeConferenceID;
    
    /**
     * Logs a user in and gets their permissions.
     */
    private LoggedUser() {
        permissions = new TreeSet<PermissionLevel>(new PermissionLevelComparator());
        
    }
    
    /**
     * Gets the single instance of LoggedUser.
     *
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
     * @param user the new user
     */
    public void setUser(final User user) {
        this.user = user;
    }
    
    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Gets the permissions set, the permission set holds the user's current permission levels.
     *
     * @return the permission set
     */
    public TreeSet<PermissionLevel> getPermissions() {
        return permissions;
    }
    
    /**
     * Gets the highest permission.
     *
     * @return the highest permission
     */
    public PermissionLevel getHighestPermission() {
        return permissions.last();
    }
    
    /**
     * Gets the lowest permission.
     *
     * @return the lowest permission
     */
    public PermissionLevel getLowestPermission() {
        return permissions.first();
    }
    
    public void setPermissions(final List<ConferencePermission> conferencePermission) {
        for (ConferencePermission cp : conferencePermission) {
            if (cp.getPermissionID() == PermissionLevel.AUTHOR.getPermission()) {
                permissions.add(PermissionLevel.AUTHOR);
            }
            if (cp.getPermissionID() == PermissionLevel.REVIEWER.getPermission()) {
                permissions.add(PermissionLevel.REVIEWER);
                
            }
            if (cp.getPermissionID() == PermissionLevel.SUBPROGRAM_CHAIR.getPermission()) {
                permissions.add(PermissionLevel.SUBPROGRAM_CHAIR);
                
            }
            if (cp.getPermissionID() == PermissionLevel.PROGRAM_CHAIR.getPermission()) {
                permissions.add(PermissionLevel.PROGRAM_CHAIR);
            }
        }
    }
    
    /**
     * This method "logs out" the user. It resets the user and permissions.
     */
    public void logout() {
        user = null;
        clearPermissions();
        permissions = new TreeSet<PermissionLevel>(new PermissionLevelComparator());
        activeConferenceID = 0;
        
    }
    
    public void clearPermissions() {
        permissions.clear();
    }
}
