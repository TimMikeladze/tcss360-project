
package model.conferences;

import java.sql.Timestamp;
import java.util.List;

import model.database.Database;

/**
 * This class holds all the information for a conference.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class Conference implements Comparable<Conference> {
    
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
    private Timestamp date;
    
    /**
     * The id of the conferences Program Chair.
     */
    private int programChairID;
    
    /**
     * The conferences Program Chair.
     */
    private String programChair;
    
    /**
     * The number of authors in the conference.
     */
    private int authors;
    
    /**
     * The number of reviewers in the conference.
     */
    private int reviewers;
    
    /**
     * Create a conference object given and ID.
     * 
     * @param id the id of the conference
     * @return conference or null if not exists
     */
    public static Conference conferenceFromID(final int id) {
        Conference conference = null;
        List<Conference> results = Database.getInstance()
                                           .createQuery(
                                                   "SELECT c.ID, c.Name, c.Location, c.Date, cu.UserID AS ProgramChairID, CONCAT(u.Firstname, ' ', u.Lastname) AS ProgramChair, "
                                                           + "(SELECT COUNT(1) FROM conference_users AS cu WHERE cu.ConferenceID = c.ID AND cu.PermissionID = 100) AS Reviewers,"
                                                           + "(SELECT COUNT(1) FROM conference_users AS cu WHERE cu.ConferenceID = c.ID AND cu.PermissionID = 200) AS Authors "
                                                           + "FROM conferences AS c JOIN conference_users AS cu ON c.ID = cu.ConferenceID AND cu.PermissionID = 400 "
                                                           + "JOIN users AS u ON u.ID = cu.UserID WHERE c.ID = :id ORDER BY c.Date DESC")
                                           .addParameter("id", id)
                                           .executeAndFetch(Conference.class);
        if (Database.hasResults(results)) {
            conference = results.get(0);
        }
        return conference;
    }
    
    /**
     * The program chair
     * 
     * @return the conference's program chair
     */
    public String getProgramChair() {
        return programChair;
    }
    
    /**
     * Gets the id.
     * 
     * @return the id
     */
    public int getID() {
        return id;
    }
    
    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the location.
     * 
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * Gets the date.
     * 
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }
    
    /**
     * Gets the program chair id.
     * 
     * @return the program chair id
     */
    public int getProgramChairID() {
        return programChairID;
    }
    
    /**
     * Gets the number of authors.
     * 
     * @return the number of authors
     */
    public int getAuthors() {
        return authors;
    }
    
    /**
     * Gets the number of reviewers.
     * 
     * @return the number of reviewers
     */
    public int getReviewers() {
        return reviewers;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Conference other = (Conference) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(final Conference c) {
        return id - c.id;
    }
}
