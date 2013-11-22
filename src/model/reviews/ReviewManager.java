
package model.reviews;

import java.io.File;
import java.io.IOException;

import model.database.Database;
import model.database.DatabaseException;
import model.permissions.Permission;
import model.util.FileHandler;

/**
 * This class manages papers for the conferences.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
 * @author Srdjan Stojcic
 * @version 11-22-2013
 */
public class ReviewManager {
    
    /**
     * Submits a review to the conference
     * 
     * @param paperID the paper id to submit to
     * @param reviewerID the reviewer's id
     * @param file the paper's file
     * @throws DatabaseException
     * @throws IOException
     */
    @Permission(level = 200)
    public static void submitReview(final int paperID, final int reviewerID, final File file) throws DatabaseException, IOException {
        Database.getInstance()
                .createQuery(
                        "INSERT INTO reviews (PaperID, ReviewerID, SubmissionDate, File, FileExtension) VALUES (:paperID, :reviewerID, NOW(), :file, :fileExtension)")
                .addParameter("paperID", paperID)
                .addParameter("reviewerID", reviewerID)
                .addParameter("file", FileHandler.convertFileToBytes(file))
                .addParameter("fileExtension", FileHandler.getFileExtension(file))
                .executeUpdate();
    }
    
}