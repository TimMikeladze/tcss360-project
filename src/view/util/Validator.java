package view.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class used to validating fields
 */
public class Validator {
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Checks if email is valid
	 * 
	 * @param email
	 *            email to check
	 * @return returns true if valid email
	 */
	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
