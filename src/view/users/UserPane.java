
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
import model.users.User;
import view.conferences.ConferenceRow;
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
public class UserPane extends GenericPane<GridPane> implements EventHandler {
    
    /**
     * A table of the conferences a user is in.
     */
    private CustomTable<ConferenceRow> usersConferencesTable;
    
    /**
     * The conferences a user is in.
     */
    private List<Conference> listOfConferences;
    
    private int userID;
    
    private User user;
    
    /**
     * TODO
     * 
     * @param user
     * @param callbacks
     * @param mainPaneCallbacks
     * @param progressSpinnerCallbacks
     */
    public UserPane(final int userID, final Callbacks callbacks, final MainPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane(), callbacks);
        addMainPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
        
        this.userID = userID;
        
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));
        
        new LoadDataService(progressSpinnerCallbacks).start();
    }
    
    @Override
    public GenericPane<GridPane> refresh() {
        return new UserPane(userID, callbacks, mainPaneCallbacks, progressSpinnerCallbacks);
    }
    
    /**
     * TODO
     */
    private void create() {
        
        Text userNameText = new Text("Name: " + user.getFullName());
        pane.add(userNameText, 0, 0);
        Text userEmailText = new Text("Email: " + user.getEmail());
        pane.add(userEmailText, 0, 1);
        
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
    
    private class LoadDataService extends ProgressSpinnerService {
        
        public LoadDataService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
            super(progressSpinnerCallbacks);
        }
        
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                
                @Override
                protected String call() {
                    try {
                        user = User.userFromID(userID);
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
                create();
            }
            super.succeeded();
        }
    }
    
}
