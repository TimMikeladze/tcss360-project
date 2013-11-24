
package view.main;

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
import view.main.conferences.ConferenceRow;
import view.util.GenericPane;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;

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
    
    private TableView<?> myPapersTable;
    
    private TableView<?> myReviewsTable;
    
    private Button viewPaperButton;
    
    private Button viewReviewButton;
    
    /**
     * An observable list for the conferences TableView.
     */
    private ObservableList<ConferenceRow> data;
    
    /**
     * Column names of conferences TableView.
     */
    private String[] columnNames = { "Conference Name", "Program Chair", "Authors", "Reviewers", "Date" };
    
    /**
     * The Database variables used to populate the conferences TableView.
     */
    private String[] variableNames = { "name", "programChair", "authors", "reviewers", "date" };
    
    /**
     * The widths of the conferences TableView.
     */
    private Integer[] columnWidths = { 30, 25, 10, 10, 25 };
    
    /**
     * The list of conferences.
     */
    private List<Conference> conferences;
    
    /**
     * Constructs a new HomePane pane that extends GridPane and displays the initial user
     * interface the user is greeted with upon login in.
     */
    public HomePane(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane());
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
        
        myConferencesTable = new TableView<ConferenceRow>();
        myConferencesTable.setOnMouseClicked(this);
        
        data = FXCollections.observableArrayList();
        
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
        new LoadConferenceService(progressSpinnerCallbacks).start();
        
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
    private void populateTable() {
        TableColumn<ConferenceRow, String> column;
        for (int i = 0; i < columnNames.length; i++) {
            column = new TableColumn<ConferenceRow, String>(columnNames[i]);
            // column.setMinWidth(columnWidths[i]);
            column.prefWidthProperty()
                  .bind(myConferencesTable.widthProperty()
                                          .divide(100 / columnWidths[i]));
            
            column.setCellValueFactory(new PropertyValueFactory<ConferenceRow, String>(variableNames[i]));
            
            myConferencesTable.getColumns()
                              .add(column);
        }
        
        for (Conference c : conferences) {
            data.add(new ConferenceRow(c.getID(), c.getName(), c.getLocation(), c.getDate(), c.getProgramChair(), c.getAuthors(), c.getReviewers()));
        }
        
        myConferencesTable.setItems(data);
    }
    
    @Override
    public void handle(final Event event) {
        if (event.getSource() == myConferencesTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                //TODO replace with entering conference
                System.out.println(myConferencesTable.getSelectionModel()
                                                     .getSelectedItem()
                                                     .getID());
            }
            
        }
    }
    
    private class LoadConferenceService extends ProgressSpinnerService {
        
        public LoadConferenceService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
            super(progressSpinnerCallbacks);
        }
        
        /**
         * Starts a new task for logging in.
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
                        conferences = ConferenceManager.getConferences();
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
         * Called when a login is successful to transition the GUI.
         */
        @Override
        protected void succeeded() {
            populateTable();
            super.succeeded();
        }
    }
    
}
