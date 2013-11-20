
package controller.user;

import java.util.HashSet;
import java.util.Set;

import model.permissions.PermissionLevel;
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
     * Data Structure holding the permissions of the user.
     */
    private HashSet<PermissionLevel> permissions;
    
    /**
     * Logs a user in and gets their permissions.
     */
    private LoggedUser() {
        permissions = new HashSet<PermissionLevel>();
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
    public Set<PermissionLevel> getPermissions() {
        return permissions;
    }
    
    /**
     * This method "logs out" the user. It resets the user and permissions.
     */
    public void logout() {
        user = null;
        permissions = new HashSet<PermissionLevel>();
    }
}
