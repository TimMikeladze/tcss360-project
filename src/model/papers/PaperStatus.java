
package model.papers;

/**
 * This class holds all the information for a paper.
 * 
 * @author Mohammad Juma
 * @version 11-18-2013
 */
public enum PaperStatus {
    
    /**
     * Accepted status.
     */
    ACCEPTED(2),
    
    /**
     * Rejected status.
     */
    REJECTED(1),
    
    /**
     * Undecided status (default).
     */
    UNDECIDED(0);
    
    /**
     * The papers status.
     */
    private int paperStatus;
    
    /**
     * Sets the papers status.
     * 
     * @param paperStatus the papers status
     */
    private PaperStatus(final int paperStatus) {
        this.paperStatus = paperStatus;
    }
    
    /**
     * Gets the papers status.
     * 
     * @return the papers status.
     */
    public int getStatus() {
        return paperStatus;
    }
    
    public String getStringValue() {
        String value = "Pending";
        if (paperStatus == 2) {
            value = "Accepted";
        }
        else if (paperStatus == 1) {
            value = "Rejected";
        }
        return value;
    }
}
