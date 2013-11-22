
package model.database;

/**
 * Enum of Database errors.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public enum Errors {
    
    /**
     * Error for emails that are already registered.
     */
    EMAIL_EXISTS("This email already exists in the database"),
    
    /**
     * Error for emails that are not already registered.
     */
    EMAIL_DOES_NOT_EXIST("This email does not exist"),
    
    /**
     * Error for conferences that dont exist.
     */
    CONFERENCE_DOES_NOT_EXIST("This conference does not exist"),
    
    /**
     * Error for submitting more than the allowed number of pepers per conference.
     */
    MAX_PAPER_SUBMISSIONS_EXCEEDED("You've submitted the maximum amount of papers allowed into this conference"),
    
    RECOMMENDATION_DOES_NOT_EXIST("A recommendation hasn't been submitted for this paper");
    
    /**
     * The error.
     */
    private String error;
    
    /**
     * Returns an error.
     * 
     * @param the error
     */
    private Errors(final String error) {
        this.error = error;
    }
    
    /**
     * Returns the error.
     * 
     * @return the error
     */
    public String getError() {
        return error;
    }
    
    /**
     * Returns the error.
     * 
     * @return the error
     */
    @Override
    public String toString() {
        return error;
    }
}
