
package view.main.conferences;

import java.sql.Timestamp;

/**
 * TODO
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class ConferenceRow {
    
    private String name;
    private String location;
    private Timestamp date;
    private String programChair;
    private int authors = 0;
    private int reviewers = 0;
    
    public ConferenceRow(String name, String location, Timestamp date, String programChair,
            int authors, int reviewers) {
        super();
        this.name = name;
        this.location = location;
        this.date = date;
        this.programChair = programChair;
        this.authors = authors;
        this.reviewers = reviewers;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }
    
    /**
     * @return the programChair
     */
    public String getProgramChair() {
        return programChair;
    }
    
    /**
     * @return the authors
     */
    public int getAuthors() {
        return authors;
    }
    
    /**
     * @return the reviewers
     */
    public int getReviewers() {
        return reviewers;
    }
}