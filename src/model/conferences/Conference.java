
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
     * The id of the Conference.
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
     * The name of the conferences Program Chair.
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
     * Create a conference object given an id.
     * 
     * <dt><b>Precondition:</b><dd> requires id > 0;
     * <dt><b>Postcondition:</b><dd> ensures A Conference if the Conference exists. <br>
     *                               ensures Null if the Conference does not exist.
     * @param id the id of the conference
     * @return the conference or null if not exists
     */
    public static Conference conferenceFromID(final int id) {
        Conference conference = null;
        List<Conference> results = Database
                .getInstance()
                .createQuery(
                        "SELECT c.ID, c.Name, c.Location, c.Date, cu.UserID AS ProgramChairID, CONCAT(u.Firstname, ' ', u.Lastname) AS ProgramChair, "
                                + "(SELECT COUNT(1) FROM conference_users AS cu WHERE cu.ConferenceID = c.ID AND cu.PermissionID = 100) AS Reviewers,"
                                + "(SELECT COUNT(1) FROM conference_users AS cu WHERE cu.ConferenceID = c.ID AND cu.PermissionID = 200) AS Authors "
                                + "FROM conferences AS c JOIN conference_users AS cu ON c.ID = cu.ConferenceID AND cu.PermissionID = 400 "
                                + "JOIN users AS u ON u.ID = cu.UserID WHERE c.ID = :id ORDER BY c.Date DESC")
                .addParameter("id", id).executeAndFetch(Conference.class);
        if (Database.hasResults(results)) {
            conference = results.get(0);
        }
        return conference;
    }
    
    /**
     * Gets the Program Chairs name.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The name of the Program Chair is returned.
     * @return the Program Chairs name
     */
    public String getProgramChair() {
        return programChair;
    }
    
    /**
     * Gets the Conferences Id.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The id of the Conference is returned.
     * @return the Conferences Id
     */
    public int getID() {
        return id;
    }
    
    /**
     * Gets the Conferences name.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The name of the Conference is returned.
     * @return the Conferences name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the Conferences location.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The location of the Conference is returned.
     * @return the Conferences location
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * Gets the Conferences date.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The date of the Conference is returned.
     * @return the Conferences date
     */
    public String getDate() {
        return date.toString().split("\\s+")[0].toString();
    }
    
    /**
     * Gets the Conferences Program Chair's Id.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The id of the Program Chair of the Conference is returned.
     * @return the Conferences Program Chair's Id
     */
    public int getProgramChairId() {
        return programChairID;
    }
    
    /**
     * Gets the number of authors in the Conference.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The number of authors of the Conference is returned.
     * @return the number of authors in the Conference
     */
    public int getAuthors() {
        return authors;
    }
    
    /**
     * Gets the number of reviewers in the Conference.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The number of reviewers of the Conference is returned.
     * @return the number of reviewers in the Conference
     */
    public int getReviewers() {
        return reviewers;
    }
    
    /**
     * Returns the hashCode of this object.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures The hash code of this object is returned.
     * @return the hashCode of this object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    
    /**
     * Checks this objects contents with another object of the same type.
     * 
     * <dt><b>Precondition:</b><dd> requires object == instanceOF Conference;
     * <dt><b>Postcondition:</b><dd> ensures The date of the Conference is returned.
     * @param object The object to test.
     * @return true if both objects are equal.
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        Conference other = (Conference) object;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
    /**
     * Compares this Conferences id with another.
     * 
     * <dt><b>Precondition:</b><dd> requires conference != null;
     * <dt><b>Postcondition:</b><dd> ensures 0 if the two conferences are the same.
     * @param conference The Conference to compare to
     * @return 0 if the same Conference
     */
    @Override
    public int compareTo(final Conference conference) {
        return id - conference.id;
    }
}
