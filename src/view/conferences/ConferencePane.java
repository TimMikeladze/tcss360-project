
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
import view.papers.PaperRow;
import view.users.UserRow;
import view.util.CustomTable;
import view.util.GenericPane;
import view.util.MainPaneCallbacks;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
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
    private final String[] conferencePapersColumnolumnNames = { "Paper Name", "Date" };
    
    /**
     * The Database variables used to populate the conference papers TableView.
     */
    private final String[] conferencePapersVariableNames = { "paperName", "date" };
    
    /**
     * Column names of conference users TableView.
     */
    private final String[] conferenceUsersColumnNames = { "First Name", "Last Name", "Role" };
    
    /**
     * The Database variables used to populate the conference users TableView.
     */
    private final String[] conferenceUsersVariableNames = { "firstName", "lastName", "role" };
    
    /**
     * The name of the conference.
     */
    private final Text conferenceNameText;
    
    /**
     * The location of the conference.
     */
    private final Text conferenceLocationText;
    
    /**
     * The date of the conference.
     */
    private final Text conferenceDateText;
    
    /**
     * The program chair of the conference.
     */
    private final Text conferenceProgramChairText;
    
    /**
     * The number of authors in the conference.
     */
    private final Text authorsText;
    
    /**
     * The number of reviewers in the conference.
     */
    private final Text reviewersText;
    
    /**
     * The id of the conference.
     */
    private final int conferenceID;
    
    /**
     * List of papers in the conference.
     */
    private CustomTable<PaperRow> conferencePapersTable;
    
    /**
     * List of users in the conference.
     */
    private CustomTable<UserRow> conferenceUsersTable;
    
    /**
     * The list of papers in the conference.
     */
    private List<Paper> listOfPapers;
    
    /**
     * The list of users in the conference.
     */
    private List<ConferenceUser> listOfUsers;
    
    private Button removeConferenceButton;
    
    private Button addSubprogramChairButton;
    
    private Button addReviewerButton;
    
    private Button uploadPaperButton;
    
    private Button uploadReviewButton;
    
    private Button viewPaperButton;
    
    private Button viewUserButton;
    
    private Button assignPaperButton;
    
    /**
     * Constructs a new Conference Pane that extends GridPane and displays the information about
     * the given conference.
     */
    public ConferencePane(final Conference conference,
            final MainPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane());
        addMainPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
        
        conferenceID = conference.getID();
        conferenceNameText = new Text("Conference: " + conference.getName());
        conferenceLocationText = new Text("Location: " + conference.getLocation());
        conferenceDateText = new Text("Date: " + conference.getDate().toString());
        conferenceProgramChairText = new Text("Program Chair: " + conference.getProgramChair());
        authorsText = new Text("Authors: " + Integer.toString(conference.getAuthors()));
        reviewersText = new Text("Reviewers: " + Integer.toString(conference.getReviewers()));
        
        conferencePapersTable = new CustomTable<PaperRow>(conferencePapersColumnolumnNames,
                conferencePapersVariableNames);
        conferenceUsersTable = new CustomTable<UserRow>(conferenceUsersColumnNames,
                conferenceUsersVariableNames);
        
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));
        
        create();
    }
    
    /**
     * Creates the main components of the ConferencePane pane.
     */
    private void create() {
        new LoadDataService(progressSpinnerCallbacks).start();
        
        pane.add(conferenceNameText, 0, 0);
        pane.add(conferenceLocationText, 1, 0);
        pane.add(conferenceDateText, 0, 1);
        pane.add(conferenceProgramChairText, 1, 1);
        pane.add(authorsText, 0, 2);
        pane.add(reviewersText, 1, 2);
        
        Text conferencePapersText = new Text("Conference Papers");
        //   conferencePapersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(conferencePapersText, 0, 3);
        conferencePapersTable.setOnMouseClicked(this);
        pane.add(conferencePapersTable, 0, 4);
        
        Text conferenceUsersText = new Text("Conference Users");
        //  conferenceUsersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(conferenceUsersText, 0, 5);
        conferenceUsersTable.setOnMouseClicked(this);
        pane.add(conferenceUsersTable, 0, 6);
        
        removeConferenceButton = new Button("Remove Conference");
        addSubprogramChairButton = new Button("Add Subprogram Chair");
        addReviewerButton = new Button("Add Reviewer");
        assignPaperButton = new Button("Assign Paper");
        uploadPaperButton = new Button("Upload Paper");
        uploadReviewButton = new Button("Upload Review");
        
        HBox bottomBox = new HBox(12);
        bottomBox.getChildren().add(removeConferenceButton);
        bottomBox.getChildren().add(addSubprogramChairButton);
        bottomBox.getChildren().add(addReviewerButton);
        bottomBox.getChildren().add(uploadPaperButton);
        bottomBox.getChildren().add(uploadReviewButton);
        
        pane.add(bottomBox, 0, 7);
    }
    
    /**
     * Populates the tables with data from the database.
     */
    private void populate() {
        if (listOfPapers != null) {
            for (Paper p : listOfPapers) {
                conferencePapersTable.add(new PaperRow(p.getPaperID(), p.getTitle(), p
                        .getSubmissionDate()));
            }
        }
        if (listOfUsers != null) {
            for (ConferenceUser u : listOfUsers) {
                //conferenceUsersTable.add(new UserRow(u.getUserID(), u.getUser().getFirstName(),
                //      u.getUser().getLastName(), u.get));
            }
        }
    }
    
    /**
     * Event handler for handling table and button click events.
     */
    @Override
    public void handle(final Event event) {
        if (event.getSource() == conferencePapersTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int paperID = conferencePapersTable.getSelectionModel().getSelectedItem()
                        .getId();
                System.out.println(paperID);
            }
        }
        if (event.getSource() == conferenceUsersTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int userID = conferenceUsersTable.getSelectionModel().getSelectedItem().getID();
                System.out.println(userID);
            }
        }
    }
    
    /**
     * Loads conference, paper, and review data from database.
     */
    private class LoadDataService extends ProgressSpinnerService {
        
        /**
         * Creates a new LoadDataService.
         * @param progressSpinnerCallbacks Spinner that spins during database query.
         */
        public LoadDataService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
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
                        int id = LoggedUser.getInstance().getUser().getID();
                        listOfPapers = PaperManager.getPapers(conferenceID);
                        listOfUsers = ConferenceManager.getUsersInConference(conferenceID);
                        
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
         * Called when data loading is done to populate tables
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
