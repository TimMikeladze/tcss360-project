
package view.papers;

import java.sql.Timestamp;

/**
 * TODO
 * 
 * @author Tim Mikeladze
 * @version 11-25-2013
 */
public class PaperRow {
    
    private final int id;
    
    private final String paperName;
    
    private final String conferenceName;
    
    private final String reviewed;
    
    private final Timestamp date;
    
    /**
     * @param id
     * @param paperName
     * @param conferenceName
     * @param reviewed
     * @param revised
     * @param date
     */
    public PaperRow(final int id, final String paperName, final String conferenceName,
            final String reviewed, final Timestamp date) {
        this.id = id;
        this.paperName = paperName;
        this.conferenceName = conferenceName;
        this.reviewed = reviewed;
        this.date = date;
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
    
}