
package view.papers;

/**
 * Wrapper class around paper data in a conference
 * 
 * @author Tim Mikeladze
 * @version 11-25-2013
 */
public class PaperRow {
    
    private int id;
    
    private String paperName;
    
    private String conferenceName;
    
    private String reviewed;
    
    private String date;
    
    private String revised;
    
    private String isRecommended;
    
    private String isAccepted;
    
    private String subprogramChair;
    
    /**
     * TODO HomePane
     * 
     * @param id
     * @param paperName
     * @param conferenceName
     * @param reviewed
     * @param revised
     * @param date
     */
    public PaperRow(final int id, final String paperName, final String conferenceName,
            final String reviewed, final int revised, final String date) {
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
     * TODO ConferencePane
     * 
     * @param id
     * @param paperName
     * @param subprogramChair
     * @param date
     * @param isRecommendedString
     * @param isAccepted
     */
    public PaperRow(final int id, final String paperName, final String subprogramChair,
            final String date, final String isRecommendedString, final String isAccepted) {
        this.id = id;
        this.paperName = paperName;
        this.subprogramChair = subprogramChair;
        this.date = date;
        this.isRecommended = isRecommendedString;
        this.isAccepted = isAccepted;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return the paperName
     */
    public String getPaperName() {
        return paperName;
    }
    
    public String getSubprogramChair() {
        return subprogramChair;
    }
    
    /**
     * @return the conferenceName
     */
    public String getConferenceName() {
        return conferenceName;
    }
    
    /**
     * @return the reviewed
     */
    public String isReviewed() {
        return reviewed;
    }
    
    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }
    
    public String getRevised() {
        return revised;
    }
    
    public String isRecommended() {
        return isRecommended;
    }
    
    public String isAccepted() {
        return isAccepted;
    }
    
}