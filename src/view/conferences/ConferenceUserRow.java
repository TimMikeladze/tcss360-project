
package view.conferences;

/**
 * The Class ConferenceUserRow.
 */
public class ConferenceUserRow {
    
    private int userID;
    
    private String name;
    
    private String role;
    
    /**
     * Instantiates a new conference user row.
     * 
     * @param userID the user id
     * @param name the name
     * @param role the role
     */
    public ConferenceUserRow(final int userID, final String name, final String role) {
        this.userID = userID;
        this.name = name;
        this.role = role;
    }
    
    /**
     * Gets the id.
     * 
     * @return the userID
     */
    public int getID() {
        return userID;
    }
    
    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the role.
     * 
     * @return the role
     */
    public String getRole() {
        return role;
    }
}
