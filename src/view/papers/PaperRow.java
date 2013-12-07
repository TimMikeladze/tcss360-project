
package view.papers;

/**
 * Wrapper class around paper data in a conference
 * 
 * @author Srdjan S
 * @version 11-25-2013
 */
public class PaperRow {
    
    /**
     * The papers id.
     */
    private int id;
    
    /**
     * The papers name.
     */
    private String paperName;
    
    /**
     * The conference's name.
     */
    private String conferenceName;
    
    /**
     * The review status of the paper.
     */
    private String reviewed;
    
    /**
     * The date the paper was submitted.
     */
    private String date;
    
    /**
     * The revision status of the paper.
     */
    private String revised;
    
    /**
     * Whether the paper has been recommended.
     */
    private String recommended;
    
    /**
     * Whether the paper has been accepted.
     */
    private String accepted;
    
    /**
     * The papers Subprogram Chair.
     */
    private String subprogramChair;
    
    /**
     * Creates a Paper Row that displays all fields available to be displaying in the HomePane.
     * 
     * @param id The id of the paper
     * @param paperName The papers name
     * @param conferenceName The conference's name
     * @param reviewed The reviewed state of the paper
     * @param revised The revised state of the paper
     * @param date The date of submission of the paper
     */
    public PaperRow(final int id, final String paperName, final String conferenceName, final String reviewed, final int revised, final String date) {
        this.id = id;
        this.paperName = paperName;
        this.conferenceName = conferenceName;
        this.reviewed = reviewed;
        if (revised > 0) {
            this.revised = "Yes";
        }
        else {
            this.revised = "No";
        }
        this.date = date;
    }
    
    /**
     * Creates a Paper Row that displays all fields available to be displaying in the
     * ConferencePane.
     * 
     * @param id The id of the paper
     * @param paperName The papers name
     * @param subprogramChair The Subprogram Chair of the paper
     * @param date The date of submission of the paper
     * @param isRecommendedString The is recommended state of the paper
     * @param isAccepted The accepted state of the paper
     */
    public PaperRow(final int id, final String paperName, final String subprogramChair, final String date, final String isRecommendedString,
            final String isAccepted) {
        this.id = id;
        this.paperName = paperName;
        this.subprogramChair = subprogramChair;
        this.date = date;
        this.recommended = isRecommendedString;
        this.accepted = isAccepted;
    }
    
    /**
     * Creates a Paper Row that displays all fields available to be displaying in the pane for
     * selecting a reviewer or Subprogram Chair..
     * 
     * @param id The id of the paper
     * @param paperName The papers name
     */
    public PaperRow(final int id, final String paperName) {
        this.id = id;
        this.paperName = paperName;
    }
    
    /**
     * The id.
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * The papers name.
     * 
     * @return the paperName
     */
    public String getPaperName() {
        return paperName;
    }
    
    /**
     * The Subprogram Chair.
     * 
     * @return subprogramChair
     */
    public String getSubprogramChair() {
        return subprogramChair;
    }
    
    /**
     * The conference's name.
     * 
     * @return the conferenceName
     */
    public String getConferenceName() {
        return conferenceName;
    }
    
    /**
     * The reviewed state.
     * 
     * @return the reviewed
     */
    public String isReviewed() {
        return reviewed;
    }
    
    /**
     * The submission date.
     * 
     * @return the date
     */
    public String getDate() {
        return date;
    }
    
    /**
     * If the paper was revised.
     * 
     * @return revised
     */
    public String getRevised() {
        return revised;
    }
    
    /**
     * Whether the paper was recommended or not
     * 
     * @return isRecommended
     */
    public String isRecommended() {
        return recommended;
    }
    
    /**
     * Whether the paper was accepted or not.
     * 
     * @return isAccepted
     */
    public String isAccepted() {
        return accepted;
    }
    
}