
package view.papers;

import java.util.List;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.conferences.ConferenceManager;
import model.conferences.ConferenceUser;
import model.permissions.PermissionLevel;
import view.conferences.AddUserCallback;
import view.conferences.ConferenceUserRow;
import view.util.CustomTable;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;

/**
 * Constructs a new window that allows the user to choose a reviewer.
 * 
 * @author Tim Mikeladze
 * @version 11-24-2013
 */
public class AddReviewersPane extends Stage implements EventHandler {
    
    /**
     * Number of clicks for a double click.
     */
    private static final int DOUBLE_CLICK = 2;
    
    /**
     * The default width.
     */
    private static final int DEFAULT_WIDTH = 400;
    
    /**
     * The default height.
     */
    private static final int DEFAULT_HEIGHT = 400;
    
    /**
     * Column names of conference users TableView.
     */
    private static final String[] usersColumnNames = { "Name", "Role" };
    
    /**
     * The Database variables used to populate the conference users TableView.
     */
    private static final String[] usersVariableNames = { "name", "role" };
    
    /**
     * The root pane.
     */
    private BorderPane root;
    
    /**
     * The scene.
     */
    private Scene scene;
    
    /**
     * The table of users to choose from.
     */
    private CustomTable<ConferenceUserRow> usersTable;
    
    /**
     * The list of users.
     */
    private List<ConferenceUser> listOfUser;
    
    /**
     * The progress spinner.
     */
    private ProgressSpinnerCallbacks progressSpinnerCallback;
    
    /**
     * TODO is this needed?
     */
    private AddUserCallback addUserCallback;
    
    /**
     * The conference id.
     */
    private int conferenceID;
    
    /**
     * Creates a new pane for choosing a new reviewer.
     * 
     * @param conferenceID The conference id.
     * @param ownerThe window that created this class.
     * @param progressSpinnerCallback A callback to the progress spinner
     * @param addUserCallback TODO hmm
     */
    public AddReviewersPane(final int conferenceID, final Stage owner,
            final ProgressSpinnerCallbacks progressSpinnerCallback, final AddUserCallback addUserCallback) {
        this.conferenceID = conferenceID;
        this.progressSpinnerCallback = progressSpinnerCallback;
        this.addUserCallback = addUserCallback;
        
        root = new BorderPane();
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        usersTable = new CustomTable<ConferenceUserRow>(usersColumnNames, usersVariableNames);
        usersTable
                .setStyle("-fx-selection-bar: khaki; -fx-selection-bar-border: goldenrod; -fx-cell-focus-inner-border: goldenrod;"
                        + "-fx-selection-bar-text: black; -fx-base: indianred; -fx-cell-hover-color: lightgrey;");
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
    }
    
    /**
     * Displays the choose dialog.
     */
    public void showDialog() {
        final Text conferenceUsersText = new Text("Conference Users");
        conferenceUsersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        usersTable.setOnMouseClicked(this);
        root.setCenter(usersTable);
        
        setScene(scene);
        show();
        
        new LoadUsers(progressSpinnerCallback).start();
    }
    
    /**
     * Populates the users table.
     */
    private void populateTable() {
        if (listOfUser != null) {
            for (ConferenceUser u : listOfUser) {
                usersTable.add(new ConferenceUserRow(u.getUserID(), u.getFullName(), u.getRole()));
            }
            usersTable.updateItems();
        }
    }
    
    /**
     * Event handler for handling user input.
     * 
     * @param event The event that occurred
     */
    @Override
    public void handle(final Event event) {
        Object source = event.getSource();
        if (source == usersTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                close();
                addUserCallback.addReviewer(usersTable.getSelectionModel().getSelectedItem().getID());
            }
        }
    }
    
    /**
     * Loads the users for choosing a reviewer.
     * 
     * @author Tim Mikeladze
     * @version 11-24-2013
     */
    private class LoadUsers extends ProgressSpinnerService {
        
        /**
         * Creates a new LoadDataService.
         * 
         * @param progressSpinnerCallbacks Spinner that spins during database query.
         */
        public LoadUsers(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
            super(progressSpinnerCallbacks);
        }
        
        /**
         * Creates a new task for loading table lists.
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
                        listOfUser = ConferenceManager.getUsersInConference(conferenceID, PermissionLevel.REVIEWER);
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when data loading is done to populate tables
         */
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                populateTable();
            }
            super.succeeded();
        }
    }
}
