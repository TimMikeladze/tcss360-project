
package view.conferences;

import java.sql.Timestamp;

/**
 * Wrapper for confenece row data in the table
 *
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class ConferenceRow {

    private int id;
    private String name;
    private String location;
    private Timestamp date;
    private String programChair;
    private int authors = 0;
    private int reviewers = 0;

    public ConferenceRow(final int id, final String name, final String location, final Timestamp date, final String programChair, final int authors,
            final int reviewers) {
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
     *
     * @return the id
     */
    public int getID() {
        return id;
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