
package view.users;

public class UserRow {
    
    private final int userID;
    
    private final String firstName;
    
    private final String lastName;
    
    private final String fullName;
    
    private final String email;
    
    private final String role;
    
    /**
     * @param userID
     * @param firstName
     * @param lastName
     * @param fullName
     * @param email
     * @param role
     */
    public UserRow(final int userID, final String firstName, final String lastName,
            final String fullName, final String email, final String role) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
    
    /**
     * @return the userID
     */
    public int getUserID() {
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
