package view.users;

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
    public UserRow(final int userID, final String firstName,
            final String lastName, final String fullName, final String email,
            final String role) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public UserRow(int userID, String firstName, String lastName,
            String fullName, String email) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
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
