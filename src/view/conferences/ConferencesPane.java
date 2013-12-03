
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
import view.util.CenterPaneCallbacks;
import view.util.CustomTable;
import view.util.GenericPane;
import view.util.MessageDialog;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
import view.util.SceneCallbacks;

/**
 * JavaFX pane responsible for displaying all conferences in the database.
 * 
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class ConferencesPane extends GenericPane<GridPane> implements EventHandler {
    
    /**
     * Number of clicks for a double click.
     */
    private static final int DOUBLE_CLICK = 2;
    
    /**
     * Column names of conferences in TableView.
     */
    private static final String[] conferencesColumnNames = { "Conference Name", "Program Chair", "Authors",
            "Reviewers", "Date" };
    
    /**
     * The variable names for the conferences table used by Java FX's table classes
     */
    private static final String[] conferencesVariableNames = { "name", "programChair", "authors", "reviewers", "date" };
    
    /**
     * A table for all the conferences in the application.
     */
    private CustomTable<ConferenceRow> conferencesTable;
    
    /**
     * The list of conferences in the application.
     */
    private List<Conference> listOfConferences;
    
    /**
     * A button for adding conferences.
     */
    private Button addConferenceButton;
    
    /**
     * Constructs a new Conferences Pane that extends GridPane and displays the information about
     * all the conferences as well as allows the user to create a new one.
     * 
     * @param sceneCallback A callback to the scene this pane is in
     * @param centerPaneCallback A callback to the center pane
     * @param progressSpinnerCallback A callback to the progress spinner
     */
    public ConferencesPane(final SceneCallbacks sceneCallback, final CenterPaneCallbacks centerPaneCallback,
            final ProgressSpinnerCallbacks progressSpinnerCallback) {
        super(new GridPane(), sceneCallback);
        addCenterPaneCallBacks(centerPaneCallback);
        addProgressSpinnerCallBack(progressSpinnerCallback);
        
        conferencesTable = new CustomTable<ConferenceRow>(conferencesColumnNames, conferencesVariableNames);
        
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
        return new ConferencesPane(sceneCallback, centerPaneCallback, progressSpinnerCallback);
    }
    
    /**
     * Creates the main components of the ConfrencesPane pane.
     */
    private void create() {
        final Text myConferencesText = new Text("All Conferences");
        myConferencesText.setId("header2");
        conferencesTable.setOnMouseClicked(this);
        pane.add(myConferencesText, 0, 0);
        pane.add(conferencesTable, 0, 1);
        
        final HBox bottomBox = new HBox(12);
        addConferenceButton = new Button("Add Conference");
        addConferenceButton.setOnAction(this);
        bottomBox.getChildren().add(addConferenceButton);
        
        pane.add(bottomBox, 0, 2);
    }
    
    /**
     * Populates the tables from the database.
     */
    private void populate() {
        if (listOfConferences != null) {
            for (Conference conference : listOfConferences) {
                conferencesTable.add(new ConferenceRow(conference.getID(), conference.getName(), conference
                        .getLocation(), conference.getDate(), conference.getProgramChair(), conference.getAuthors(),
                        conference.getReviewers()));
            }
            conferencesTable.updateItems();
        }
    }
    
    /**
     * Handles user input.
     * 
     * @param event The event that occurred
     */
    @Override
    public void handle(final Event event) {
        final Object source = event.getSource();
        if (source == conferencesTable) {
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int conferenceID = conferencesTable.getSelectionModel().getSelectedItem().getID();
                //TODO binary search
                centerPaneCallback.pushPane(new ConferencePane(conferenceID, sceneCallback, centerPaneCallback,
                        progressSpinnerCallback));
            }
        }
        else if (source == addConferenceButton) {
            centerPaneCallback.pushPane(new CreateConferencePane(sceneCallback, centerPaneCallback,
                    progressSpinnerCallback));
        }
    }
    
    /**
     * Loads conference data from database.
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
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(e.getMessage(), false);
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
                create();
                populate();
            }
            super.succeeded();
        }
    }
}
