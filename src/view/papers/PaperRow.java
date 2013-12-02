
package view.papers;

import java.sql.Timestamp;

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
    
    private Timestamp date;
    
    private String revised;
    
    private String isRecommended;
    
    /**
     * @param id
     * @param paperName
     * @param conferenceName
     * @param reviewed
     * @param revised
     * @param date
     */
    public PaperRow(final int id, final String paperName, final String conferenceName,
            final String reviewed, final int revised, final Timestamp date) {
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
     * @param id
     * @param paperName
     * @param date
     */
    public PaperRow(final int id, final String paperName, final Timestamp date) {
        this.id = id;
        this.paperName = paperName;
        this.date = date;
    }
    
    /**
     * @param id
     * @param paperName
     * @param date
     * @param isRecommended 
     */
    public PaperRow(final int id, final String paperName, final Timestamp date, String isRecommendedString) {
        this.id = id;
        this.paperName = paperName;
        this.date = date;
        this.isRecommended = isRecommendedString;
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
    public Timestamp getDate() {
        return date;
    }
    
    public String getRevised() {
        return revised;
    }
    
}