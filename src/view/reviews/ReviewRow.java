
package view.reviews;


/**
 * Wrapper class around review row data in the the table
 * 
 * @author Tim Mikeladze
 * @version 11-25-2013
 */
public class ReviewRow {
    
    private int id;
    
    private String paperName;
    
    private String conferenceName;
    
    private boolean reviewed;
    
    private String author;
    
    private String reviewName;
    
    /**
     * @param id
     * @param paperName
     * @param conferenceName
     * @param reviewed
     * @param author
     */
    public ReviewRow(final int id, final String paperName, final String conferenceName,
            final boolean reviewed, final String author) {
        this.id = id;
        this.paperName = paperName;
        this.conferenceName = conferenceName;
        this.reviewed = reviewed;
        this.author = author;
    }
    
    public ReviewRow(final int id, final String reviewName) {
        this.id = id;
        this.reviewName = reviewName;
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
    
    public String getReviewName() {
        return reviewName;
    }
}