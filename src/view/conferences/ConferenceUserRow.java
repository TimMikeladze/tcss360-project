
package view.conferences;

/**
 * Wrapper for conference user row data in the table.
 * 
 * @author Mohammad Juma
 * @version 11-28-2013
 */
public class ConferenceUserRow {
    
    /**
     * The id of the conference user.
     */
    private int userID;
    
    /**
     * The name of the conference user.
     */
    private String name;
    
    /**
     * The role of the conference user.
     */
    private String role;
    
    /**
     * Creates a conference user that displays the users name and role.
     * 
     * @param userID The conference users id
     * @param name The conference users name
     * @param role The conference users role
     */
    public ConferenceUserRow(final int userID, final String name, final String role) {
        this.userID = userID;
        this.name = name;
        this.role = role;
    }
    
    /**
     * The id.
     * 
     * @return the userID
     */
    public int getID() {
        return userID;
    }
    
    /**
     * The name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * The role.
     * 
     * @return the role
     */
    public String getRole() {
        return role;
    }
}
