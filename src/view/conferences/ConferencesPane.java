
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.conferences.Conference;
import model.conferences.ConferenceManager;
import view.util.Callbacks;
import view.util.CustomTable;
import view.util.GenericPane;
import view.util.MainPaneCallbacks;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;

/**
 * JavaFX pane responsible for displaying all conferences in the database.
 * 
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class ConferencesPane extends GenericPane<GridPane> implements EventHandler {
    
    /**
     * TODO COMMENT THIS
     */
    private static final int DOUBLE_CLICK = 2;
    
    /**
     * TODO COMMENT THIS
     */
    private CustomTable<ConferenceRow> conferencesTable;
    
    /**
     * Column names of conferences in TableView.
     */
    private String[] conferencesColumnNames = { "Conference Name", "Program Chair", "Authors",
            "Reviewers", "Date" };
    
    /**
     * The variable names for the conferences table used by Java FX's table classes
     */
    private String[] conferencesVariableNames = { "name", "programChair", "authors",
            "reviewers", "date" };
    
    /**
     * The list of conferences for the table.
     */
    private List<Conference> listOfConferences;
    
    private Button addConferenceButton;
    
    public ConferencesPane(final Callbacks callbacks,
            final MainPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane(), callbacks);
        addMainPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
        
        conferencesTable = new CustomTable<ConferenceRow>(conferencesColumnNames,
                conferencesVariableNames);
        
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
        Text myConferencesText = new Text("Conferences");
        myConferencesText.setId("conf-header");
        myConferencesText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        conferencesTable.setOnMouseClicked(this);
        pane.add(myConferencesText, 0, 0);
        pane.add(conferencesTable, 0, 1);
        
        HBox bottomBox = new HBox(12);
        
        addConferenceButton = new Button("Add Conference");
        addConferenceButton.setOnAction(this);
        
        bottomBox.getChildren().add(addConferenceButton);
        
        pane.add(bottomBox, 0, 7);
        
        new LoadDataService(progressSpinnerCallbacks).start();
        
    }
    
    /**
     * Populates the tables from the database.
     */
    private void populate() {
        if (listOfConferences != null) {
            for (Conference c : listOfConferences) {
                conferencesTable.add(new ConferenceRow(c.getID(), c.getName(), c.getLocation(),
                        c.getDate(), c.getProgramChair(), c.getAuthors(), c.getReviewers()));
            }
            conferencesTable.updateItems();
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
                int conferenceID = conferencesTable.getSelectionModel().getSelectedItem()
                        .getID();
                //TODO binary search
                mainPaneCallbacks.pushPane(new ConferencePane(Conference
                        .conferenceFromID(conferenceID), callbacks, mainPaneCallbacks,
                        progressSpinnerCallbacks));
            }
        }
        if (event.getSource() == addConferenceButton) {
            mainPaneCallbacks.pushPane(new CreateConferencePane(callbacks, mainPaneCallbacks,
                    progressSpinnerCallbacks));
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
}
