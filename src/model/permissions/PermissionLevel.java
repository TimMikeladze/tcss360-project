
package model.permissions;

/**
 * Permissions Enum.
 * 
 * @author Tim Mikeladze
 * @version 11-18-2013
 */
public enum PermissionLevel {
    
    /**
     * The author permission level.
     */
    AUTHOR(100),
    
    /**
     * The reviewer permission level.
     */
    REVIEWER(200),
    
    /**
     * The Subprogram Chair permission level.
     */
    SUBPROGRAM_CHAIR(300),
    
    /**
     * The Program Chair permission level.
     */
    PROGRAM_CHAIR(400),
    
    /**
     * The admin permission level.
     */
    ADMIN(500);
    
    /**
     * The permission level.
     */
    private final int permission;
    
    /**
     * Sets the permission level.
     * 
     * @param permission the permission level
     */
    private PermissionLevel(final int permission) {
        this.permission = permission;
    }
    
    /**
     * Gets the permission.
     * 
     * @return the permission
     */
    public int getPermission() {
        return permission;
    }
    
    public static String getRole(final PermissionLevel permission) {
        String role = "";
        switch (permission) {
            case AUTHOR:
                role = "Author";
                break;
            case REVIEWER:
                role = "Reviewer";
                break;
            case SUBPROGRAM_CHAIR:
                role = "Subprogram Chair";
                break;
            case PROGRAM_CHAIR:
                role = "Program Chair";
                break;
            case ADMIN:
                role = "Admin";
                break;
            default:
                break;
        }
        return role;
        
    }
}
