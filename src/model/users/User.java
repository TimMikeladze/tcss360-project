
package model.users;

import java.util.List;

import model.database.Database;

/**
 * Class that holds information about a user.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class User {
    
    /**
     * The id of the user.
     */
    private int id;
    
    /**
     * The users first name.
     */
    private String firstName;
    
    /**
     * The users last name.
     */
    private String lastName;
    
    /**
     * The users email.
     */
    private String email;
    
    /**
     * Create a user object given an email
     * 
     * @param email the user's email
     * @return the User object, null if user with given email does not exist
     */
    public static User userFromEmail(final String email) {
        User user = null;
        List<User> results = Database
                .getInstance()
                .createQuery(
                        "SELECT ID, Firstname, Lastname, Email FROM users WHERE Email = :email")
                .addParameter("email", email).executeAndFetch(User.class);
        if (Database.hasResults(results)) {
            user = results.get(0);
        }
        return user;
    }
    
    /**
     * Create a user object given an id.
     * 
     * @param email the user's id
     * @return the User object, null if user with given id does not exist
     */
    public static User userFromID(final int id) {
        User user = null;
        List<User> results = Database.getInstance()
                .createQuery("SELECT ID, Firstname, Lastname, Email FROM users WHERE ID = :id")
                .addParameter("id", id).executeAndFetch(User.class);
        if (Database.hasResults(results)) {
            user = results.get(0);
        }
        return user;
    }
    
    /**
     * Gets the id.
     * 
     * @return the id
     */
    public int getID() {
        return id;
    }
    
    /**
     * Gets the first name.
     * 
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Gets the last name.
     * 
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Gets the email.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Gets the users full name.
     * 
     * @return the users full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Gets the toString of a User.
     * 
     * @return all relevant information on a user
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email=" + email + "]";
    }
}
