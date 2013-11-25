
package view.main;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.conferences.Conference;
import model.conferences.ConferenceManager;
import model.papers.Paper;
import model.reviews.Review;
import view.main.conferences.ConferencePane;
import view.main.conferences.ConferenceRow;
import view.papers.PaperRow;
import view.reviews.ReviewRow;
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
    
    /**
     * The conference list TableView.
     */
    private TableView<ConferenceRow> myConferencesTable;
    
    private TableView<PaperRow> myPapersTable;
    
    private TableView<ReviewRow> myReviewsTable;
    
    private Button viewPaperButton;
    
    private Button viewReviewButton;
    
    /**
     * An observable list for the conferences TableView.
     */
    private ObservableList<ConferenceRow> conferenceData;
    
    private ObservableList<PaperRow> paperData;;
    
    private ObservableList<ReviewRow> reviewData;
    
    /**
     * Column names of conferences TableView.
     */
    private String[] conferencesColumnNames = { "Conference Name", "Program Chair", "Authors",
            "Reviewers", "Date" };
    
    /**
     * The Database variables used to populate the conferences TableView.
     */
    private String[] conferencesVariableNames = { "name", "programChair", "authors",
            "reviewers", "date" };
    
    private String[] papersColumnNames = { "Paper Name", "Conference Name", "Reviewed",
            "Revised", "Submission Date" };
    
    private String[] papersVariableNames = { "paperName", "conferenceName", "reviewed",
            "revised", "date" };
    
    private String[] reviewsColumnsNames = { "Paper Name", "Author", "Conference Name",
            "Reviewed", "Submission Date" };
    
    private String[] reviewsVariableNames = { "paperName", "author", "conferenceName",
            "reviewed", "date" };
    
    /**
     * The widths of the conferences TableView.
     */
    private Integer[] columnWidths = { 30, 25, 10, 10, 25 };
    
    /**
     * The list of conferences.
     */
    private List<Conference> conferences;
    
    private List<Paper> papers;
    
    private List<Review> reviews;
    
    /**
     * Constructs a new HomePane pane that extends GridPane and displays the initial user
     * interface the user is greeted with upon login in.
     */
    public HomePane(final MainPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane());
        addMainPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
        
        myConferencesTable = new TableView<ConferenceRow>();
        myConferencesTable.setOnMouseClicked(this);
        
        conferenceData = FXCollections.observableArrayList();
        
        myPapersTable = new TableView();
        myReviewsTable = new TableView();
        viewPaperButton = new Button("View Paper");
        viewReviewButton = new Button("View Review");
        
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
        pane.add(myConferencesTable, 0, 1);
        
        Text myPapersText = new Text("My Papers");
        myPapersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(myPapersText, 0, 3);
        pane.add(myPapersTable, 0, 4);
        pane.add(viewPaperButton, 0, 5);
        
        Text myReviewsText = new Text("My Reviews");
        myReviewsText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(myReviewsText, 0, 6);
        pane.add(myReviewsTable, 0, 7);
        pane.add(viewReviewButton, 0, 8);
    }
    
    /**
     * Populates the conference table.
     */
    private void populateConferencesTable() {
        TableColumn<ConferenceRow, String> column;
        for (int i = 0; i < conferencesColumnNames.length; i++) {
            column = new TableColumn<ConferenceRow, String>(conferencesColumnNames[i]);
            // column.setMinWidth(columnWidths[i]);
            column.prefWidthProperty().bind(
                    myConferencesTable.widthProperty().divide(100 / columnWidths[i]));
            
            column.setCellValueFactory(new PropertyValueFactory<ConferenceRow, String>(
                    conferencesVariableNames[i]));
            
            myConferencesTable.getColumns().add(column);
        }
        
        for (Conference c : conferences) {
            conferenceData.add(new ConferenceRow(c.getID(), c.getName(), c.getLocation(), c
                    .getDate(), c.getProgramChair(), c.getAuthors(), c.getReviewers()));
        }
        myConferencesTable.setItems(conferenceData);
    }
    
    /**
     * TODO add conference name to paper
     * Populates the papers table.
     */
    private void populatePapersTable() {
        TableColumn<PaperRow, String> column;
        for (int i = 0; i < papersColumnNames.length; i++) {
            column = new TableColumn<PaperRow, String>(papersColumnNames[i]);
            // column.setMinWidth(columnWidths[i]);
            column.prefWidthProperty().bind(
                    myConferencesTable.widthProperty().divide(100 / columnWidths[i]));
            
            column.setCellValueFactory(new PropertyValueFactory<PaperRow, String>(
                    papersVariableNames[i]));
            
            myPapersTable.getColumns().add(column);
        }
        
        for (Paper p : papers) {
            paperData.add(new PaperRow(p.getPaperID(), p.getTitle(), "", p.getStatus()
                    .getStringValue(), p.getSubmissionDate()));
        }
        myPapersTable.setItems(paperData);
    }
    
    /**
     * TODO add conference name and paper name
     * Populates the reviews table.
     */
    private void populateReviewsTable() {
        TableColumn<ReviewRow, String> column;
        for (int i = 0; i < reviewsColumnsNames.length; i++) {
            column = new TableColumn<ReviewRow, String>(reviewsColumnsNames[i]);
            // column.setMinWidth(columnWidths[i]);
            column.prefWidthProperty().bind(
                    myReviewsTable.widthProperty().divide(100 / columnWidths[i]));
            
            column.setCellValueFactory(new PropertyValueFactory<ReviewRow, String>(
                    reviewsVariableNames[i]));
            
            myReviewsTable.getColumns().add(column);
        }
        
        for (Review r : reviews) {
            reviewData.add(new ReviewRow(r.getId(), "", "", true, "", new Timestamp(Calendar
                    .getInstance().getTime().getTime())));
        }
        myReviewsTable.setItems(reviewData);
    }
    
    @Override
    public void handle(final Event event) {
        if (event.getSource() == myConferencesTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                //TODO replace with entering conference
                int conferenceID = myConferencesTable.getSelectionModel().getSelectedItem()
                        .getID();
                System.out.println(conferenceID);
                mainPaneCallbacks.changeCenterPane(new ConferencePane(Conference
                        .conferenceFromID(conferenceID)));
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
                        conferences = ConferenceManager.getConferencesForUser(LoggedUser
                                .getInstance().getUser().getID());
                        //papers = PaperManager.getPapers(LoggedUser.getInstance().getUser().getID());
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
                populateConferencesTable();
                //populatePapersTable();
                //populateReviewsTable();
            }
            super.succeeded();
        }
    }
    
}
