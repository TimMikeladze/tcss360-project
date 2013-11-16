package model.papers;

import java.io.File;

import model.conferences.ConferenceManager;
import model.database.Database;
import model.database.DatabaseException;
import model.database.Errors;
import model.permissions.Permissions;

/**
 * The Class PaperManager.
 */
public class PaperManager {
	private static final int MAX_PAPERS = 4;

	/**
	 * Submits a paper to the conference
	 * 
	 * @param conferenceID
	 *            the conference id to submit to
	 * @param authorID
	 *            the author's id
	 * @param title
	 *            the paper title
	 * @param description
	 *            the paper description
	 * @param file
	 *            the paper's file
	 * @throws DatabaseException
	 */
	public static void submitPaper(int conferenceID, int authorID,
			String title, String description, File file)
			throws DatabaseException {
		// TODO needs to check if papers submission isn't past conference date
		// TODO convert file to bytes and store in database
		if (MAX_PAPERS > getNumberOfSubmittedPapers(conferenceID, authorID)) {
			Database.getInstance()
					.createQuery(
							"INSERT INTO papers (ConferenceID, AuthorID, Title, Description, SubmissionDate) VALUES (:conferenceID, :authorID, :title, :description, NOW())")
					.addParameter("conferenceID", conferenceID)
					.addParameter("authorID", authorID)
					.addParameter("title", title)
					.addParameter("description", description).executeUpdate()
					.getKey(Integer.class);
			ConferenceManager.addUserToConference(conferenceID, authorID,
					Permissions.AUTHOR);
		} else {
			throw new DatabaseException(Errors.MAX_PAPER_SUBMISSIONS_EXCEEDED);
		}
	}
	/**
	 * Gets the number of papers an author has submitted to a conference
	 * 
	 * @param conferenceID
	 *            the conference id
	 * @param authorID
	 *            the authors id
	 * @return number of papers author has submitted
	 */
	public static int getNumberOfSubmittedPapers(int conferenceID, int authorID) {
		return Database
				.getInstance()
				.createQuery(
						"SELECT COUNT(1) FROM papers WHERE ConferenceID = :conferenceID AND AuthorID = :authorID")
				.addParameter("conferenceID", conferenceID)
				.addParameter("authorID", authorID).executeAndFetchTable()
				.rows().get(0).getInteger(0);
	}

}
