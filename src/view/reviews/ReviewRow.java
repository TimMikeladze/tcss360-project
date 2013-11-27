
package view.reviews;

import java.sql.Timestamp;

/**
 * Wrapper class around review row data in the the table
 *
 * @author Tim Mikeladze
 * @version 11-25-2013
 */
public class ReviewRow {

    private final int id;

    private final String paperName;

    private final String conferenceName;

    private final boolean reviewed;

    private final String author;

    private final Timestamp date;

    /**
     * @param id
     * @param paperName
     * @param conferenceName
     * @param reviewed
     * @param author
     * @param date
     */
    public ReviewRow(final int id, final String paperName, final String conferenceName,
            final boolean reviewed, final String author, final Timestamp date) {
        this.id = id;
        this.paperName = paperName;
        this.conferenceName = conferenceName;
        this.reviewed = reviewed;
        this.author = author;
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
    public boolean isReviewed() {
        return reviewed;
    }

    /**
     * @return the revised
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }
}