
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

public class AddReviewersPane extends Stage implements EventHandler {
    
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
     * The root pane.
     */
    private BorderPane root;
    
    /**
     * The scene.
     */
    private Scene scene;
    
    /**
     * The list of users.
     */
    private List<ConferenceUser> listOfUser;
    
    /**
     * Column names of conference users TableView.
     */
    private final String[] usersColumnNames = { "Name", "Role" };
    
    /**
     * The Database variables used to populate the conference users TableView.
     */
    private final String[] usersVariableNames = { "name", "role" };
    
    private CustomTable<ConferenceUserRow> usersTable;
    
    private ProgressSpinnerCallbacks progressSpinnerCallbacks;
    
    private AddUserCallback addUserCallback;
    
    private int conferenceID;
    
    public AddReviewersPane(final int conferenceID, final Stage owner, final ProgressSpinnerCallbacks progressSpinnerCallbacks,
            final AddUserCallback addUserCallback) {
        this.conferenceID = conferenceID;
        this.progressSpinnerCallbacks = progressSpinnerCallbacks;
        this.addUserCallback = addUserCallback;
        
        root = new BorderPane();
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        usersTable = new CustomTable<ConferenceUserRow>(usersColumnNames, usersVariableNames);
        usersTable.setStyle("-fx-selection-bar: khaki; -fx-selection-bar-border: goldenrod; -fx-cell-focus-inner-border: goldenrod;"
                + "-fx-selection-bar-text: black; -fx-base: indianred; -fx-cell-hover-color: lightgrey;");
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
    }
    
    public void showDialog() {
        Text conferenceUsersText = new Text("Conference Users");
        conferenceUsersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        usersTable.setOnMouseClicked(this);
        
        root.setCenter(usersTable);
        
        setScene(scene);
        show();
        
        new LoadUsers(progressSpinnerCallbacks).start();
    }
    
    /**
     * Populates the users table.
     */
    private void populateTable() {
        if (listOfUser != null) {
            for (ConferenceUser u : listOfUser) {
                usersTable.add(new ConferenceUserRow(u.getUserID(), u.getUsername(), u.getRole()));
            }
            usersTable.updateItems();
        }
    }
    
    @Override
    public void handle(final Event event) {
        Object source = event.getSource();
        if (source == usersTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                close();
                addUserCallback.addReviewer(usersTable.getSelectionModel()
                                                      .getSelectedItem()
                                                      .getID());
            }
        }
    }
    
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
                        // TODO show error
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
