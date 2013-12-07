
package view.users;
/**
 * Wrapper class around user row data in the the table
 * 
 * @author Srdjan 
 * @version 11-30-13
 */
public class UserRow {
    
    private int userID;
    
    private String firstName;
    
    private String lastName;
    
    private String fullName;
    
    private String email;
    
    private String role;
    
    /**
     * @param userID
     * @param firstName
     * @param lastName
     * @param fullName
     * @param email
     * @param role
     */
    public UserRow(final int userID, final String firstName, final String lastName, final String email,
            final String role) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;;
        this.email = email;
        this.role = role;
    }
    
    /**
     * @param userID
     * @param firstName
     * @param lastName
     * @param role
     */
    public UserRow(final int userID, final String firstName, final String lastName) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
    
    /**
     * @return the userID
     */
    public int getID() {
        return userID;
    }
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }
}
