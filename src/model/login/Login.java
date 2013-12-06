
package model.login;

import model.database.Database;
import model.database.DatabaseErrors;
import model.database.DatabaseException;
import model.users.User;

/**
 * Handles user login and registration.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class Login {
    
    /**
     * Checks to see if a user is registered given an email.
     * 
     * <dt><b>Precondition:</b><dd> requires email != null;
     * <dt><b>Postcondition:</b><dd> ensures True if the user is registered, otherwise false.
     * @param email the email to check
     * @return true if user is registered
     */
    public static boolean isRegistered(final String email) {
        return Database.hasResults(Database.getInstance().createQuery("SELECT 1 FROM users WHERE Email = :email")
                .addParameter("email", email.trim()).executeAndFetchTable());
    }
    
    /**
     * Registers a user.
     * 
     * <dt><b>Precondition:</b><dd> requires firstName != null; <br>
     *                              requires lastName != null; <br>
     *                              requires email != null; <br>
     *                              requires The user does not already exist.
     * <dt><b>Postcondition:</b><dd> ensures The new user is registered and their id is returned.
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email
     * @throws DatabaseException if email already exists in DB
     */
    public static int registerUser(final String firstName, final String lastName, final String email)
            throws DatabaseException {
        int id;
        if (!isRegistered(email)) {
            id = Database
                    .getInstance()
                    .createQuery(
                            "INSERT INTO users (Firstname, Lastname, Email) VALUES (:firstName, :lastName, :email)")
                    .addParameter("firstName", firstName.trim()).addParameter("lastName", lastName.trim())
                    .addParameter("email", email.trim()).executeUpdate().getKey(Integer.class);
        }
        else {
            throw new DatabaseException(DatabaseErrors.EMAIL_EXISTS);
        }
        return id;
    }
    
    /**
     * Logs in a user and returns the corresponding User object.
     * 
     * <dt><b>Precondition:</b><dd> requires email != null; <br>
     *                              requires The user already is registered.
     * <dt><b>Postcondition:</b><dd> ensures The User's corresponding User Object is returned.
     * @param email the user's email
     * @return the User object or null if user for given email doesn't exist
     * @throws DatabaseException if email doesn't exist in DB
     */
    public static User loginUser(final String email) throws DatabaseException {
        User user = null;
        if (isRegistered(email)) {
            user = User.userFromEmail(email);
        }
        else {
            throw new DatabaseException(DatabaseErrors.EMAIL_DOES_NOT_EXIST);
        }
        return user;
    }
}
