
package view.users;

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
import model.database.DatabaseException;
import model.papers.Paper;
import model.papers.PaperManager;
import model.permissions.PermissionLevel;
import view.papers.PaperRow;
import view.util.CustomTable;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;

/**
 * JavaFX pane responsible for displaying the users registered in the database.
 * 
 * @version 11-23-2013
 */
public class UsersPane extends Stage implements EventHandler {
    
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
     * Column names of papers in the conference.
     */
    private static final String[] papersColumnNames = { "Name" };
    
    /**
     * The Database variables used to populate the papers TableView.
     */
    private static final String[] papersVariableNames = { "name" };
    
    /**
     * Column names of conference users TableView.
     */
    private static final String[] usersColumnNames = { "First Name", "Last Name" };
    
    /**
     * The Database variables used to populate the conference users TableView.
     */
    private static final String[] usersVariableNames = { "firstName", "lastName" };
    
    /**
     * The conferences id.
     */
    private final int conferenceId;
    
    /**
     * The root pane.
     */
    private BorderPane root;
    
    /**
     * The scene.
     */
    private Scene scene;
    
    /**
     * The table of papers in the conference.
     */
    private CustomTable<PaperRow> papersTable;
    
    /**
     * The table of users to choose from.
     */
    private CustomTable<UserRow> usersTable;
    
    /**
     * The list of papers.
     */
    private List<Paper> listOfPapers;
    
    /**
     * The list of users.
     */
    private List<ConferenceUser> listOfUsers;
    
    /**
     * The progress spinner.
     */
    private ProgressSpinnerCallbacks progressSpinnerCallbacks;
    
    /**
     * The permission level.
     */
    private PermissionLevel permissionLevel;
    
    /**
     * The selected paper.
     */
    private int paperId;
    
    /**
     * Creates the pane.
     * 
     * @param owner The window that called this class=
     * @param progressSpinnerCallback The progress spinner
     * @param conferenceId The conference id
     * @param permission The permission
     */
    public UsersPane(final Stage owner, final ProgressSpinnerCallbacks progressSpinnerCallback, final int conferenceId,
            final PermissionLevel permission) {
        this.progressSpinnerCallbacks = progressSpinnerCallback;
        this.conferenceId = conferenceId;
        this.permissionLevel = permission;
        
        root = new BorderPane();
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        papersTable = new CustomTable<PaperRow>(papersColumnNames, papersVariableNames);
        papersTable
                .setStyle("-fx-selection-bar: khaki; -fx-selection-bar-border: goldenrod; -fx-cell-focus-inner-border: goldenrod;"
                        + "-fx-selection-bar-text: black; -fx-base: indianred; -fx-cell-hover-color: lightgrey;");
        usersTable = new CustomTable<UserRow>(usersColumnNames, usersVariableNames);
        usersTable
                .setStyle("-fx-selection-bar: khaki; -fx-selection-bar-border: goldenrod; -fx-cell-focus-inner-border: goldenrod;"
                        + "-fx-selection-bar-text: black; -fx-base: indianred; -fx-cell-hover-color: lightgrey;");
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
    }
    
    /**
     * Shows the dialog for the user to select a paper and a user.
     */
    public void showDialog() {
        final BorderPane leftPane = new BorderPane();
        final Text selectPaperText = new Text("Select Paper");
        selectPaperText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        leftPane.setTop(selectPaperText);
        leftPane.setCenter(papersTable);
        root.setLeft(leftPane);
        papersTable.setOnMouseClicked(this);
        
        final BorderPane rightPane = new BorderPane();
        final Text selectUserText = new Text("Select User");
        selectUserText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        rightPane.setTop(selectUserText);
        rightPane.setCenter(usersTable);
        root.setRight(rightPane);
        usersTable.setOnMouseClicked(this);
        
        setScene(scene);
        show();
        
        new LoadPapers(progressSpinnerCallbacks, conferenceId).start();
    }
    
    /**
     * Populates the users table.
     */
    private void populateTable() {
        if (listOfPapers != null) {
            for (Paper paper : listOfPapers) {
                papersTable.add(new PaperRow(paper.getPaperID(), paper.getTitle()));
            }
            papersTable.updateItems();
        }
        if (listOfUsers != null) {
            for (ConferenceUser user : listOfUsers) {
                usersTable.add(new UserRow(user.getUserID(), user.getFirstname(), user.getLastname()));
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
        final Object source = event.getSource();
        if (source == papersTable) {
            paperId = papersTable.getSelectionModel().getSelectedItem().getId();
            new LoadUsers(progressSpinnerCallbacks, conferenceId, paperId, permissionLevel).start();
        }
        if (source == usersTable) {
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                close();
                new AddUserService(progressSpinnerCallbacks, conferenceId, usersTable.getSelectionModel()
                        .getSelectedItem().getID(), permissionLevel).start();
            }
        }
    }
    
    /**
     * Loads the papers from the database.
     * 
     * @author Mohammad Juma
     * @version 12-03-2013
     */
    private class LoadPapers extends ProgressSpinnerService {
        
        /**
         * The conference id.
         */
        private final int conferenceId;
        
        /**
         * Loads the papers into the table.
         * 
         * @param progressSpinnerCallback Spinner that spins during database query
         * @param conferenceId the conference id
         */
        public LoadPapers(final ProgressSpinnerCallbacks progressSpinnerCallback, final int conferenceId) {
            super(progressSpinnerCallback);
            this.conferenceId = conferenceId;
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
                        listOfPapers = PaperManager.getPapers(conferenceId);
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
    
    /**
     * Loads the users from the database.
     * 
     * @author Mohammad Juma
     * @version 12-03-2013
     */
    private class LoadUsers extends ProgressSpinnerService {
        
        /**
         * The conference id.
         */
        private final int conferenceId;
        
        /**
         * The selected paper.
         */
        private final int paperId;
        
        /**
         * The users permission.
         */
        private final PermissionLevel permission;
        
        /**
         * Loads the users into the table.
         * 
         * @param progressSpinnerCallbacks Spinner that spins during database query.
         * @param conferenceId the conference id
         * @param permission the users permission
         */
        public LoadUsers(final ProgressSpinnerCallbacks progressSpinnerCallbacks, final int conferenceId,
                final int paperId, final PermissionLevel permission) {
            super(progressSpinnerCallbacks);
            this.conferenceId = conferenceId;
            this.paperId = paperId;
            this.permission = permission;
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
                        populateUsers();
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        
                    }
                    return null;
                }
            };
        }
        
        /**
         * Populate the users list with users from the database while removing ineligible users.
         */
        private void populateUsers() {
            listOfUsers = ConferenceManager.getUsersInConference(conferenceId);
            if (permission == PermissionLevel.REVIEWER) {
                for (int i = 0; i < listOfUsers.size(); i++) {
                    try {
                        if (listOfUsers.get(i).getUserID() == PaperManager.getPaperAuthorID(paperId)) {
                            listOfUsers.remove(i);
                        }
                    }
                    catch (DatabaseException e) {
                        // TODO What error is this?
                        e.printStackTrace();
                    }
                }
            }
            else if (permission == PermissionLevel.SUBPROGRAM_CHAIR) {
                for (int i = 0; i < listOfUsers.size(); i++) {
                    if (listOfUsers.get(i).getAssignedAsSubProgramChair() >= 4) {
                        listOfUsers.remove(i);
                    }
                }
            }
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
    
    /**
     * Adds a user to the conference
     * 
     * @author Mohammad Juma
     * @version 12-01-2013
     */
    private class AddUserService extends ProgressSpinnerService {
        
        /**
         * The conference id.
         */
        private int conferenceID;
        
        /**
         * The users id.
         */
        private int userID;
        
        /**
         * The permission level.
         */
        private PermissionLevel permission;
        
        /**
         * Creates the service.
         * 
         * @param progressSpinnerCallbacks
         * @param ConferenceID
         * @param UserID
         * @param permission
         */
        public AddUserService(final ProgressSpinnerCallbacks progressSpinnerCallbacks, final int ConferenceID,
                final int UserID, final PermissionLevel permission) {
            super(progressSpinnerCallbacks);
            
            this.conferenceID = ConferenceID;
            this.userID = UserID;
            this.permission = permission;
        }
        
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                
                /**
                 * Calls the new task.
                 */
                @Override
                protected String call() {
                    try {
                        ConferenceManager.addUserToConference(conferenceID, userID, permission);
                        setSuccess(true);
                        System.out.println("adding " + userID);
                    }
                    catch (Exception e) {
                        //TODO make sure message dialog works
                        //new MessageDialog(callbacks.getPrimaryStage()).showDialog(
                        //        e.getMessage(), false);
                        
                    }
                    return null;
                }
            };
        }
        
        @Override
        protected void succeeded() {
            if (getSuccess()) {
            }
            super.succeeded();
        }
    }
}
