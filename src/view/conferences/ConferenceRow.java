
package view.conferences;

/**
 * Wrapper for conference row data in the table.
 *
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class ConferenceRow {
    
    /**
     * The id of the conference.
     */
    private int id;
    
    /**
     * The name of the conference.
     */
    private String name;
    
    /**
     * The location of the conference.
     */
    private String location;
    
    /**
     * The date of the conference.
     */
    private String date;
    
    /**
     * The program chair of the conference.
     */
    private String programChair;
    
    /**
     * The number of authors in the conference.
     */
    private int authors = 0;
    
    /**
     * The number of reviewers in the conference.
     */
    private int reviewers = 0;
    
    /**
     * Creates a conference row that displays all fields available.
     * 
     * @param id The id of the conference
     * @param name The name of the conference
     * @param location The location of the conference
     * @param date The date of the conference
     * @param programChair The program chair of the conference
     * @param authors The number of authors in the conference
     * @param reviewers  The number of reviewers in the conference
     */
    public ConferenceRow(final int id, final String name, final String location, final String date,
            final String programChair, final int authors, final int reviewers) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.programChair = programChair;
        this.authors = authors;
        this.reviewers = reviewers;
    }
    
    /**
     * Creates a conference row that only displays the name, date and Program Chair.
     * 
     * @param id The id of the conference
     * @param name The name of the conference
     * @param date The date of the conference
     * @param programChair The program chair of the conference
     */
    public ConferenceRow(final int id, final String name, final String date, final String programChair) {
        super();
        this.id = id;
        this.name = name;
        this.date = date;
        this.programChair = programChair;
    }
    
    /**
     * The id.
     * 
     * @return the id
     */
    public int getID() {
        return id;
    }
    
    /**
     * The name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * The location.
     * 
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * The date.
     * 
     * @return the date
     */
    public String getDate() {
        return date;
    }
    
    /**
     * The Program Chair.
     * 
     * @return the programChair
     */
    public String getProgramChair() {
        return programChair;
    }
    
    /**
     * The number of authors.
     * 
     * @return the number of authors
     */
    public int getAuthors() {
        return authors;
    }
    
    /**
     * The number of reviewers.
     * 
     * @return the number of reviewers
     */
    public int getReviewers() {
        return reviewers;
    }
}