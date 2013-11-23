
package controller.user;

import java.util.TreeSet;

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
     * Adds a permission.
     * 
     * @param permission the permission
     */
    public void addPermission(final PermissionLevel permission) {
        permissions.add(permission);
    }
    
    /**
     * Removes a permission;
     * 
     * @param permission the permission
     */
    public void removePermissions(final PermissionLevel permission) {
        permissions.remove(permission);
    }
    
    public PermissionLevel getHighestPermission() {
        return permissions.last();
    }
    
    public PermissionLevel getLowestPermission() {
        return permissions.first();
    }
    
    /**
     * This method "logs out" the user. It resets the user and permissions.
     */
    public void logout() {
        user = null;
        permissions = new TreeSet<PermissionLevel>(new PermissionLevelComparator());
    }
}
