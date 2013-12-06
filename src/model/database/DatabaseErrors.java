
package model.database;

/**
 * Enum of Database errors.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public enum DatabaseErrors {
    
    /**
     * Error for email's that are already registered.
     */
    EMAIL_EXISTS("This email already exists in the database"),
    
    /**
     * Error for email's that are not already registered.
     */
    EMAIL_DOES_NOT_EXIST("This email does not exist"),
    
    /**
     * Error for conferences that don't exist.
     */
    CONFERENCE_DOES_NOT_EXIST("This conference does not exist"),
    
    /**
     * Error for submitting more than the allowed number of papers per conference.
     */
    MAX_PAPER_SUBMISSIONS_EXCEEDED("You've submitted the maximum amount of papers allowed into this conference"),
    
    /**
     * Error for recommendation not existing.
     */
    RECOMMENDATION_DOES_NOT_EXIST("A recommendation hasn't been submitted for this paper"),
    
    /**
     * Error for user already existing in the Conference.
     */
    USER_ALREADY_IN_CONFERENCE("A user with this permission level already exists in this conference"),
    
    /**
     * Error for the user not being a Reviewer.
     */
    USER_NOT_REVIEWER("A user must be a reviewer to be designated a subprogram chair"),
    
    /**
     * Error for the paper not existing.
     */
    PAPER_DOES_NOT_EXIST("This paper does not exist"),
    
    /**
     * Error for the user not being able to be assigned as a Subprogram Chair to their own paper. 
     */
    CANT_ASSIGN_PAPER("A user can't be assigned to a paper that they authored"),
    
    /**
     * Error for the user not being able to be assigned as a Reviewer to their own paper. 
     */
    CANT_REVIEW_PAPER("A user can't review a paper that they authored"),
    
    /**
     * Error for submitting a Paper to a Conference in the past.
     */
    PAST_CONFERENCE_DATE("You cannot submit a paper to a conference after conference date.");
    
    /**
     * The error.
     */
    private String error;
    
    /**
     * Returns an error.
     * 
     * <dt><b>Precondition:</b><dd> requires errors != null;
     * <dt><b>Postcondition:</b><dd> ensures The proper error is assigned.
     * @param the error
     */
    private DatabaseErrors(final String error) {
        this.error = error;
    }
    
    /**
     * Gets the error.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The proper error is returned.
     * @return the error
     */
    public String getError() {
        return error;
    }
    
    /**
     * Returns the error.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The proper error is returned.
     * @return the error
     */
    @Override
    public String toString() {
        return error;
    }
}
