
package view.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class used to validating fields.
 * 
 * @author Cathryn
 * @version 11-17-2013
 */
public class Validator {
    
    /**
     * The email regex.
     */
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    /**
     * Checks if email is valid.
     * 
     * @param email email to check
     * @return returns true if valid email
     */
    public static boolean isValidEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    /**
     * Checks if a string in array of strings is empty
     * 
     * @param strings the array of strings
     * @return true, if empty string found
     */
    public static boolean isEmpty(final String... strings) {
        boolean empty = false;
        for (String s : strings) {
            if (s == null) {
                empty = true;
                break;
            }
            else if (s.trim()
                      .isEmpty()) {
                empty = true;
                break;
            }
        }
        return empty;
    }
}
