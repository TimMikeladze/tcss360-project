
package view.home;

import java.util.List;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.conferences.Conference;
import model.conferences.ConferenceManager;
import model.papers.Paper;
import model.papers.PaperManager;
import model.reviews.Review;
import view.conferences.ConferencePane;
import view.conferences.ConferenceRow;
import view.papers.PaperRow;
import view.reviews.ReviewRow;
import view.util.CustomTable;
import view.util.GenericPane;
import view.util.MainPaneCallbacks;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
import controller.user.LoggedUser;

/**
 * JavaFX pane responsible for displaying the users home interface.
 *
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class HomePane extends GenericPane<GridPane> implements EventHandler {

    private static final int DOUBLE_CLICK = 2;

    private CustomTable<ConferenceRow> conferencesTable;

    private CustomTable<PaperRow> papersTable;

    private CustomTable<ReviewRow> reviewsTable;

    /**
     * Column names of conferences TableView.
     */
    private String[] conferencesColumnNames = { "Conference Name", "Program Chair", "Authors", "Reviewers", "Date" };

    /**
     * The variable names used by Java FX's table classes
     */
    private String[] conferencesVariableNames = { "name", "programChair", "authors", "reviewers", "date" };

    private String[] papersColumnNames = { "Paper Name", "Conference Name", "Reviewed", "Revised", "Submission Date" };

    private String[] papersVariableNames = { "paperName", "conferenceName", "reviewed", "revised", "date" };

    private String[] reviewsColumnsNames = { "Paper Name", "Author", "Conference Name", "Reviewed", "Submission Date" };

    private String[] reviewsVariableNames = { "paperName", "author", "conferenceName", "reviewed", "date" };

    /**
     * The list of conferences.
     */
    private List<Conference> conferences;

    /**
     * The list of papers.
     */
    private List<Paper> papers;

    /**
     * The list of reviews.
     */
    private List<Review> reviews;

    /**
     * Constructs a new HomePane pane that extends GridPane and displays the initial user
     * interface the user is greeted with upon login in.
     */
    public HomePane(final MainPaneCallbacks mainPaneCallbacks, final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane());
        addMainPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);

        mainPaneCallbacks.setCreateConferenceButtonVisible(true);

        conferencesTable = new CustomTable<ConferenceRow>(conferencesColumnNames, conferencesVariableNames);
        conferencesTable.setOnMouseClicked(this);

        papersTable = new CustomTable<PaperRow>(papersColumnNames, papersVariableNames);
        reviewsTable = new CustomTable<ReviewRow>(reviewsColumnsNames, reviewsVariableNames);

        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));

        create();
    }

    /**
     * Creates the main components of the HomePane pane.
     */
    private void create() {
        new LoadDataService(progressSpinnerCallbacks).start();

        Text myConferencesText = new Text("My Conferences");
        myConferencesText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(myConferencesText, 0, 0);
        pane.add(conferencesTable, 0, 1);

        Text myPapersText = new Text("My Papers");
        myPapersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(myPapersText, 0, 3);
        pane.add(papersTable, 0, 4);

        Text myReviewsText = new Text("My Reviews");
        myReviewsText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(myReviewsText, 0, 6);
        pane.add(reviewsTable, 0, 7);
    }

    /**
     * Populates the tables.
     */
    private void populate() {
        if (conferences != null) {
            for (Conference c : conferences) {
                conferencesTable.add(new ConferenceRow(c.getID(), c.getName(), c.getLocation(), c.getDate(), c.getProgramChair(), c.getAuthors(),
                        c.getReviewers()));
            }
        }
        if (papers != null) {
            for (Paper p : papers) {
                papersTable.add(new PaperRow(p.getPaperID(), p.getTitle(), p.getConferenceName(), p.getStatus()
                                                                                                   .getStringValue(), p.getRevised(),
                        p.getSubmissionDate()));
            }
        }
    }

    @Override
    public void handle(final Event event) {
        if (event.getSource() == conferencesTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                //TODO replace with entering conference
                int conferenceID = conferencesTable.getSelectionModel()
                                                   .getSelectedItem()
                                                   .getID();
                mainPaneCallbacks.changeCenterPane(new ConferencePane(Conference.conferenceFromID(conferenceID)));
                mainPaneCallbacks.update();
            }
        }
    }

    /**
     * Loads conferences
     *
     *
     */
    private class LoadDataService extends ProgressSpinnerService {

        public LoadDataService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
            super(progressSpinnerCallbacks);
        }

        /**
         * Creates a new task for loading conferences
         */
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {

                /**
                 * Calls the new task.
                 */
                @Override
                protected String call() {
                    try {
                        int id = LoggedUser.getInstance()
                                           .getUser()
                                           .getID();
                        conferences = ConferenceManager.getConferencesForUser(id);
                        papers = PaperManager.getAuthorsSubmittedPapers(id);

                        //TODO implement reviews
                        //reviews = ReviewManager.getReviews(;
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        // TODO show error
                    }
                    return null;
                }
            };
        }

        /**
         * Called when a conference loading is done to populate table
         */
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                populate();
            }
            super.succeeded();
        }
    }

}
