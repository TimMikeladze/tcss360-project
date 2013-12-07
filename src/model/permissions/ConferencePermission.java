
package model.permissions;

/**
 * Conference Permissions
 * @author Tim Mikeladze
 * @version 12-04-2013
 */
public class ConferencePermission {
    
    /**
     * confernce id
     */
    private int conferenceID;
    
    /**
     * user id
     */
    private int userID;
    
    /**
     * permission id
     */
    private int permissionID;
    
    /**
     * Gets a conference
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A conferece id is returned
     * @return int
     */
    public int getConferenceID() {
        return conferenceID;
    }
    
    /**
     * Gets a user id
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A user id is returned
     * @return int
     */
    public int getUserID() {
        return userID;
    }
    
    /**
     * Gets a permission id
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A permission id is returned
     * @return int
     */
    public int getPermissionID() {
        return permissionID;
    }
    
    /**
     * Gets a role
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A role is returned
     * @return role
     */
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
}
