
package model.users;

import java.util.List;

import model.database.Database;

/**
 * TODO tim
 * @author Tim Mikeladze
 *
 */
public class UserManager {
    
    /**
     * TODO tim
     * @return
     */
    public static List<User> getUsers() {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT ID, Firstname, Lastname, Email FROM users ORDER BY Lastname")
                .executeAndFetch(User.class);
    }
    
}
