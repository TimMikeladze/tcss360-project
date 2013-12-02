
package view.users;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.users.User;
import view.util.Callbacks;
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
        Text headerText = new Text("User Info");
        headerText.setId("header1");
        pane.add(headerText, 0, 0);
        Text userNameText = new Text("Name: " + user.getFullName());
        userNameText.setId("conf-text");
        pane.add(userNameText, 0, 1);
        Text userEmailText = new Text("Email: " + user.getEmail());
        userEmailText.setId("conf-text");
        pane.add(userEmailText, 0, 2);
        
    }
    
    /**
     * Handles user input
     */
    @Override
    public void handle(final Event event) {
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
