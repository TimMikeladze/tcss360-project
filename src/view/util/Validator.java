
package view.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class used to validating fields
 * 
 * @author Tim Mikeladze
 * @version 11-17-2013
 */
public class Validator {
    
    /**
     * The email regex.
     */
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    /**
     * Checks if email is valid
     * 
     * @param email email to check
     * @return returns true if valid email
     */
    public static boolean isValidEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
