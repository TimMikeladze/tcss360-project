
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
import view.util.Callbacks;
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
    
    /**
     * TODO COMMENT THIS
     */
    private static final int DOUBLE_CLICK = 2;
    
    /**
     * TODO COMMENT THIS
     */
    private CustomTable<ConferenceRow> conferencesTable;
    
    /**
     * TODO COMMENT THIS
     */
    private CustomTable<PaperRow> papersTable;
    
    /**
     * TODO COMMENT THIS
     */
    private CustomTable<ReviewRow> reviewsTable;
    
    /**
     * Column names of conferences in TableView.
     */
    private String[] conferencesColumnNames = { "Conference Name", "Program Chair", "Authors", "Reviewers", "Date" };
    
    /**
     * The variable names for the conferences table used by Java FX's table classes
     */
    private String[] conferencesVariableNames = { "name", "programChair", "authors", "reviewers", "date" };
    
    /**
     * Column names of papers in TableView.
     */
    private String[] papersColumnNames = { "Paper Name", "Conference Name", "Reviewed", "Revised", "Submission Date" };
    
    /**
     * The variable names for the papers table used by Java FX's table classes
     */
    private String[] papersVariableNames = { "paperName", "conferenceName", "reviewed", "revised", "date" };
    
    /**
     * Column names of reviews in TableView.
     */
    private String[] reviewsColumnsNames = { "Paper Name", "Author", "Conference Name", "Reviewed", "Submission Date" };
    
    /**
     * The variable names for the reviews table used by Java FX's table classes
     */
    private String[] reviewsVariableNames = { "paperName", "author", "conferenceName", "reviewed", "date" };
    
    /**
     * The list of conferences for the table.
     */
    private List<Conference> listOfConferences;
    
    /**
     * The list of papers for the table.
     */
    private List<Paper> listOfPapers;
    
    /**
     * The list of reviews for the table.
     */
    private List<Review> listOfReviews;
    
    /**
     * Constructs a new HomePane pane that extends GridPane and displays the initial user
     * interface the user is greeted with upon login in.
     */
    public HomePane(final Callbacks callbacks, final MainPaneCallbacks mainPaneCallbacks, final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane(), callbacks);
        addMainPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
        
        conferencesTable = new CustomTable<ConferenceRow>(conferencesColumnNames, conferencesVariableNames);
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
        Text myConferencesText = new Text("My Conferences");
        myConferencesText.setId("conf-header");
        myConferencesText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        conferencesTable.setOnMouseClicked(this);
        pane.add(myConferencesText, 0, 0);
        pane.add(conferencesTable, 0, 1);
        
        Text myPapersText = new Text("My Papers");
        myPapersText.setId("paper-header");
        myPapersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        papersTable.setOnMouseClicked(this);
        pane.add(myPapersText, 0, 3);
        pane.add(papersTable, 0, 4);
        
        Text myReviewsText = new Text("My Reviews");
        myReviewsText.setId("rev-header");
        myReviewsText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        papersTable.setOnMouseClicked(this);
        pane.add(myReviewsText, 0, 6);
        pane.add(reviewsTable, 0, 7);
        
        new LoadDataService(progressSpinnerCallbacks).start();
        
    }
    
    /**
     * Populates the tables from the database.
     */
    private void populate() { // TODO WHY NULL?
        if (listOfConferences != null) {
            for (Conference c : listOfConferences) {
                conferencesTable.add(new ConferenceRow(c.getID(), c.getName(), c.getLocation(), c.getDate(), c.getProgramChair(), c.getAuthors(),
                        c.getReviewers()));
            }
            conferencesTable.updateItems();
        }
        if (listOfPapers != null) {
            for (Paper p : listOfPapers) {
                papersTable.add(new PaperRow(p.getPaperID(), p.getTitle(), p.getConferenceName(), p.getStatus()
                                                                                                   .getStringValue(), p.getRevised(),
                        p.getSubmissionDate()));
            }
            papersTable.updateItems();
        }
        if (listOfReviews != null) {
            for (Review r : listOfReviews) {
                //reviewsTable.add(new ReviewRow());
            }
            reviewsTable.updateItems();
        }
    }
    
    /**
     * Handles user input
     */
    @Override
    public void handle(final Event event) {
        if (event.getSource() == conferencesTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int conferenceID = conferencesTable.getSelectionModel()
                                                   .getSelectedItem()
                                                   .getID();
                //TODO binary search
                mainPaneCallbacks.pushPane(new ConferencePane(Conference.conferenceFromID(conferenceID), callbacks, mainPaneCallbacks,
                        progressSpinnerCallbacks));
            }
        }
        if (event.getSource() == papersTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int paperID = papersTable.getSelectionModel()
                                         .getSelectedItem()
                                         .getId();
            }
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int reviewID = reviewsTable.getSelectionModel()
                                           .getSelectedItem()
                                           .getId();
            }
        }
    }
    
    /**
     * Loads conference, paper, and review data from database.
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
                        listOfConferences = ConferenceManager.getConferencesForUser(id);
                        listOfPapers = PaperManager.getAuthorsSubmittedPapers(id);
                        
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
