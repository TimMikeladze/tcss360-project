
package view.conferences;

import java.util.List;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.conferences.Conference;
import model.conferences.ConferenceManager;
import model.conferences.ConferenceUser;
import model.papers.Paper;
import model.papers.PaperManager;
import model.permissions.PermissionLevel;
import model.permissions.Permissions;
import view.papers.PaperPane;
import view.papers.PaperRow;
import view.papers.UploadPaperPane;
import view.users.UserPane;
import view.users.UsersPane;
import view.util.CenterPaneCallbacks;
import view.util.CustomTable;
import view.util.GenericPane;
import view.util.MessageDialog;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
import view.util.SceneCallbacks;
import controller.user.LoggedUser;

/**
 * JavaFX pane responsible for displaying information about a selected conference.
 *
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class ConferencePane extends GenericPane<GridPane> implements EventHandler {
    
    /**
     * Number of clicks for a double click.
     */
    private static final int DOUBLE_CLICK = 2;
    
    /**
     * Column names of conference papers TableView.
     */
    private static final String[] conferencePapersColumnolumnNames = { "Paper Name", "Subprogram Chair", "Date",
            "Recommended", "State" };
    
    /**
     * The Database variables used to populate the conference papers TableView.
     */
    private static final String[] conferencePapersVariableNames = { "paperName", "subprogramChair", "date",
            "isRecommended", "isAccepted" };
    
    /**
     * Column names of conference users TableView.
     */
    private static final String[] conferenceUsersColumnNames = { "Name", "Role" };
    
    /**
     * The Database variables used to populate the conference users TableView.
     */
    private static final String[] conferenceUsersVariableNames = { "name", "role" };
    
    /**
     * The id of the conference.
     */
    private final int conferenceID;
    
    /**
     * The selected conference.
     */
    private Conference conference;
    
    /**
     * A table for the papers in the conference.
     */
    private CustomTable<PaperRow> conferencePapersTable;
    
    /**
     * A table for the users in the conference.
     */
    private CustomTable<ConferenceUserRow> conferenceUsersTable;
    
    /**
     * The list of papers in the conference.
     */
    private List<Paper> listOfPapers;
    
    /**
     * The list of users in the conference.
     */
    private List<ConferenceUser> listOfUsers;
    
    /**
     * A button for removing the conference.
     */
    private Button removeConferenceButton;
    
    /**
     * A button for assigning a Subprogram Chair to a paper.
     */
    private Button assignSubprogramChairButton;
    
    /**
     * A button for assigning a Reviewer to a paper.
     */
    private Button assignReviewerButton;
    
    /**
     * A button for adding a user to the conference.
     */
    private Button addUserToConferenceButton;
    
    /**
     * A button for uploading a paper to the conference.
     */
    private Button uploadPaperButton;
    
    /**
     * Constructs a new Conference Pane that extends GridPane and displays the information about
     * the given conference.
     * 
     * @param conferenceID The id of the conference
     * @param sceneCallback A callback to the scene this pane is in
     * @param centerPaneCallback A callback to the center pane
     * @param progressSpinnerCallback A callback to the progress spinner
     */
    public ConferencePane(final int conferenceID, final SceneCallbacks sceneCallback,
            final CenterPaneCallbacks centerPaneCallback, final ProgressSpinnerCallbacks progressSpinnerCallback) {
        super(new GridPane(), sceneCallback);
        addCenterPaneCallBacks(centerPaneCallback);
        addProgressSpinnerCallBack(progressSpinnerCallback);
        
        this.conferenceID = conferenceID;
        
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));
        
        new LoadDataService(progressSpinnerCallback).start();
    }
    
    /**
     * Refreshes the current pane after data has been changed.
     */
    @Override
    public GenericPane<GridPane> refresh() {
        return new ConferencePane(conferenceID, sceneCallback, centerPaneCallback, progressSpinnerCallback);
    }
    
    /**
     * Creates the main components of the ConferencePane pane.
     */
    private void create() {
        final Text conferenceNameText = new Text("Conference: " + conference.getName());
        conferenceNameText.setId("conf-text");
        pane.add(conferenceNameText, 0, 0);
        final Text conferenceLocationText = new Text("Location: " + conference.getLocation());
        conferenceLocationText.setId("conf-text");
        pane.add(conferenceLocationText, 1, 0);
        final Text conferenceDateText = new Text("Date: " + conference.getDate().toString());
        conferenceDateText.setId("conf-text");
        pane.add(conferenceDateText, 0, 1);
        final Text conferenceProgramChairText = new Text("Program Chair: " + conference.getProgramChair());
        conferenceProgramChairText.setId("conf-text");
        pane.add(conferenceProgramChairText, 1, 1);
        final Text authorsText = new Text("Authors: " + Integer.toString(conference.getAuthors()));
        authorsText.setId("conf-text");
        pane.add(authorsText, 0, 2);
        final Text reviewersText = new Text("Reviewers: " + Integer.toString(conference.getReviewers()));
        reviewersText.setId("conf-text");
        pane.add(reviewersText, 1, 2);
        final Text conferencePapersText = new Text("Conference Papers");
        conferencePapersText.setId("header2");
        pane.add(conferencePapersText, 0, 3);
        
        conferencePapersTable = new CustomTable<PaperRow>(conferencePapersColumnolumnNames,
                conferencePapersVariableNames);
        conferencePapersTable.setOnMouseClicked(this);
        pane.add(conferencePapersTable, 0, 4);
        
        final Text conferenceUsersText = new Text("Conference Users");
        conferenceUsersText.setId("header2");
        pane.add(conferenceUsersText, 0, 5);
        conferenceUsersTable = new CustomTable<ConferenceUserRow>(conferenceUsersColumnNames,
                conferenceUsersVariableNames);
        conferenceUsersTable.setOnMouseClicked(this);
        pane.add(conferenceUsersTable, 0, 6);
        
        removeConferenceButton = new Button("Remove Conference");
        removeConferenceButton.setOnAction(this);
        
        uploadPaperButton = new Button("Upload Paper");
        uploadPaperButton.setOnAction(this);
        
        assignSubprogramChairButton = new Button("Assign Subprogram Chair");
        assignSubprogramChairButton.setOnAction(this);
        
        assignReviewerButton = new Button("Assign Reviewer");
        assignReviewerButton.setOnAction(this);
        
        addUserToConferenceButton = new Button("Add User");
        addUserToConferenceButton.setOnAction(this);
        
        /* TODO
         * Add permissions to these buttons:
         *  if (Permissions.hasPermission(ConferenceManager.class,
         *                                "addSubProgramChairToConference",
         *                                 LoggedUser.getInstance().getPermissions())) {
         *  }        
         */
        
        final HBox bottomBox = new HBox(12);
        bottomBox.getChildren().add(removeConferenceButton);
        bottomBox.getChildren().add(assignSubprogramChairButton);
        bottomBox.getChildren().add(assignReviewerButton);
        bottomBox.getChildren().add(addUserToConferenceButton);
        bottomBox.getChildren().add(uploadPaperButton);
        
        pane.add(bottomBox, 0, 7);
    }
    
    /**
     * Populates the tables with data from the database.
     */
    private void populate() {
        if (listOfPapers != null) {
            for (Paper paper : listOfPapers) {
                conferencePapersTable.add(new PaperRow(paper.getPaperID(), paper.getTitle(),
                        paper.getSubprogramChair(), paper.getSubmissionDate(), paper.isRecommendedString(), paper
                                .isAccepted()));
            }
            conferencePapersTable.updateItems();
        }
        if (listOfUsers != null) {
            for (ConferenceUser user : listOfUsers) {
                conferenceUsersTable.add(new ConferenceUserRow(user.getUserID(), user.getUsername(), user.getRole()));
            }
            conferenceUsersTable.updateItems();
        }
    }
    
    /**
     * TODO: There has to be a better way than all these if else's
     * Event handler for handling table and button click events.
     * 
     * @param event The event that occurred
     */
    @Override
    public void handle(final Event event) {
        final Object source = event.getSource();
        if (source == conferencePapersTable) {
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int paperID = conferencePapersTable.getSelectionModel().getSelectedItem().getId();
                centerPaneCallback.pushPane(new PaperPane(paperID, sceneCallback, centerPaneCallback,
                        progressSpinnerCallback));
            }
        }
        else if (source == conferenceUsersTable) {
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int userID = conferenceUsersTable.getSelectionModel().getSelectedItem().getID();
                centerPaneCallback.pushPane(new UserPane(userID, sceneCallback, centerPaneCallback,
                        progressSpinnerCallback));
            }
        }
        else if (source == assignReviewerButton) {
            new UsersPane(sceneCallback.getPrimaryStage(), progressSpinnerCallback, conferenceID,
                    PermissionLevel.REVIEWER).showDialog();
        }
        else if (source == assignSubprogramChairButton) {
            new UsersPane(sceneCallback.getPrimaryStage(), progressSpinnerCallback, conferenceID,
                    PermissionLevel.SUBPROGRAM_CHAIR).showDialog();
        }
        else if (source == addUserToConferenceButton) {
            new UsersPane(sceneCallback.getPrimaryStage(), progressSpinnerCallback, conferenceID,
                    PermissionLevel.SUBPROGRAM_CHAIR).showDialog();
        }
        else if (source == uploadPaperButton) {
            centerPaneCallback.pushPane(new UploadPaperPane(conferenceID, sceneCallback, centerPaneCallback,
                    progressSpinnerCallback));
        }
    }
    
    /**
     * Loads conference, paper, and review data from database.
     */
    private class LoadDataService extends ProgressSpinnerService {
        
        /**
         * Creates a new LoadDataService.
         *
         * @param progressSpinnerCallback Spinner that spins during database query.
         */
        public LoadDataService(final ProgressSpinnerCallbacks progressSpinnerCallback) {
            super(progressSpinnerCallback);
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
                        LoggedUser.getInstance().setPermissions(
                                Permissions.getPermissionsForConference(conferenceID, LoggedUser.getInstance()
                                        .getUser().getID()));
                        conference = Conference.conferenceFromID(conferenceID);
                        listOfPapers = PaperManager.getPapers(conferenceID);
                        listOfUsers = ConferenceManager.getUsersInConference(conferenceID);
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(e.getMessage(), false);
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when data loading is done to populate tables.
         */
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                create();
                populate();
            }
            super.succeeded();
        }
    }
}
