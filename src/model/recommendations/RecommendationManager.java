
package model.recommendations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.database.Database;
import model.database.DatabaseErrors;
import model.database.DatabaseException;
import model.permissions.Permission;
import model.util.FileHandler;

/**
 * The Class RecommendationManager.
 */
public class RecommendationManager {
    
    /**
     * Submit recommendation for a paper.
     * 
     * <dt><b>Precondition:</b><dd> requires paper id > 0
     * <dt><b>Postcondition:</b><dd> ensures A recomendation is submitted
     * @param paperID the paper id
     * @param reviewerID the reviewer id
     * @param file the file
     * @return the inserted id of the review
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Permission(level = 300)
    public static int submitRecommendation(final int paperID, final int reviewerID,
            final File file) throws IOException {
        return Database
                .getInstance()
                .createQuery(
                        "INSERT INTO paper_recommendations (PaperID, ReviewerID, File, FileExtension) VALUES (:paperID, :reviewerID, :file, :fileExtension)")
                .addParameter("paperID", paperID).addParameter("reviewerID", reviewerID)
                .addParameter("file", FileHandler.convertFileToBytes(file))
                .addParameter("fileExtension", FileHandler.getFileExtension(file))
                .executeUpdate().getKey(Integer.class);
    }
    
    /**
     * Gets the recommendation for a paper.
     * 
     * <dt><b>Precondition:</b><dd> requires paper id > 0
     * <dt><b>Postcondition:</b><dd> ensures A recommendation is returned
     * @param paperID the paper id
     * @return the recommendation
     * @throws DatabaseException the database exception
     */
    @Permission(level = 300)
    public static Recommendation getRecommendation(final int paperID) throws DatabaseException {
        List<Recommendation> list = Database
                .getInstance()
                .createQuery(
                        "SELECT ID, PaperID, ReviewerID, File, FileExtension FROM paper_recommendations WHERE PaperID = :paperID")
                .addParameter("paperID", paperID).executeAndFetch(Recommendation.class);
        if (Database.hasResults(list)) {
            return list.get(0);
        }
        else {
            throw new DatabaseException(DatabaseErrors.RECOMMENDATION_DOES_NOT_EXIST);
        }
        
    }
    
    /**
     * Gets the recommendations for all papers in a conference.
     * 
     * <dt><b>Precondition:</b><dd> requires conference id > 0
     * <dt><b>Postcondition:</b><dd> ensures A recommendation is returned
     * @param conferenceID the conference id
     * @return the recommendations
     */
    @Permission(level = 400)
    public static List<Recommendation> getRecommendations(final int conferenceID) {
        return Database
                .getInstance()
                .createQuery(
                        "SELECT r.ID, r.PaperID, r.ReviewerID, r.File, r.FileExtension FROM paper_recommendations AS r JOIN papers AS p ON p.ID = r.PaperID WHERE p.ConferenceID = :conferenceID")
                .addParameter("conferenceID", conferenceID)
                .executeAndFetch(Recommendation.class);
        
    }
}
