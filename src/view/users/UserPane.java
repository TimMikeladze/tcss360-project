
package view.users;

import java.util.List;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.conferences.Conference;
import model.conferences.ConferenceManager;
import model.papers.Paper;
import model.reviews.Review;
import model.users.User;
import view.conferences.AddUserCallback;
import view.conferences.ConferenceRow;
import view.papers.PaperRow;
import view.reviews.ReviewRow;
import view.util.Callbacks;
import view.util.CustomTable;
import view.util.GenericPane;
import view.util.MainPaneCallbacks;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;

/**
 * JavaFX pane responsible for displaying information about a selected user.
 * 
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class UserPane extends GenericPane<GridPane> implements EventHandler, AddUserCallback {
    
    /**
     * The conference tables column names.
     */
    private static final String[] conferenceColumnNames = { "Conference Name", "Program Chair", "Date" };
    
    /**
     * The conference tables variable names.
     */
    private static final String[] conferenceVariableNames = { "name", "programChair", "date" };
    
    /**
     * The papers tables column names.
     */
    private static final String[] papersColumnNames = { "Paper Title", "Conference Name" };
    
    /**
     * The paper tables variable names.
     */
    private static final String[] papersVariableNames = {};
    
    /**
     * The review tables column names.
     */
    private static final String[] reviewColumnNames = {};
    
    /**
     * The review tables variable names.
     */
    private static final String[] reviewVariableNames = {};
    
    /**
     * The users id.
     */
    private final int id;
    
    /**
     * The users first name.
     */
    private final String userName;
    
    /**
     * The users email.
     */
    private final String email;
    
    /**
     * A table of the conferences a user is in.
     */
    private CustomTable<ConferenceRow> usersConferencesTable;
    
    /**
     * A table of the papers the user has written.
     */
    private CustomTable<PaperRow> usersPapersTable;
    
    /**
     * A table of the reviews the user has published.
     */
    private CustomTable<ReviewRow> usersReviewsTable;
    
    /**
     * The conferences a user is in.
     */
    private List<Conference> listOfConferences;
    
    /**
     * The papers the user has written.
     */
    private List<Paper> listOfPapers;
    
    /**
     * The reviews the user has published.
     */
    private List<Review> listOfReviews;
    
    /**
     * TODO
     * 
     * @param user
     * @param callbacks
     * @param mainPaneCallbacks
     * @param progressSpinnerCallbacks
     */
    public UserPane(final User user, final Callbacks callbacks, final MainPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane(), callbacks);
        addMainPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
        
        id = user.getID();
        userName = user.getFullName();
        email = user.getEmail();
        
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));
        
        create();
    }
    
    /**
     * TODO
     */
    private void create() {
        
        final Text userNameText = new Text("Name: " + userName);
        pane.add(userNameText, 0, 0);
        final Text userEmailText = new Text("Email: " + email);
        pane.add(userEmailText, 0, 1);
        
        usersConferencesTable.setOnMouseClicked(this);
        pane.add(usersConferencesTable, 0, 2);
        
        new LoadDataService(progressSpinnerCallbacks).start();
        
    }
    
    /**
     * Populates the tables from the database.
     */
    private void populate() {
        if (listOfConferences != null) {
            for (Conference conference : listOfConferences) {
                usersConferencesTable.add(new ConferenceRow(conference.getID(), conference.getName(), conference.getDate(),
                        conference.getProgramChair()));
            }
            usersConferencesTable.updateItems();
        }
    }
    
    /**
     * Handles user input
     */
    @Override
    public void handle(final Event event) {
        if (event.getSource() == usersConferencesTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == 2) { // TODO change 2 to DOUBLE_CLICK
                // TODO Do something
            }
        }
    }
    
    /**
     * Loads conference
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
                        listOfConferences = ConferenceManager.getConferences();
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        //TODO show error
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
    
    @Override
    public void addReviewer(final int userID) {
        // TODO Auto-generated method stub
        
    }
}
