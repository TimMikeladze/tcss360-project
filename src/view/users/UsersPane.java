package view.users;

import java.util.List;

import controller.user.LoggedUser;
import model.conferences.Conference;
import model.conferences.ConferenceManager;
import model.conferences.ConferenceUser;
import model.papers.PaperManager;
import model.users.User;
import model.users.UserManager;
import view.conferences.ConferenceRow;
import view.util.CustomProgressIndicator;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX pane responsible for displaying the users registered in the database.
 * 
 * @version 11-23-2013
 */
public class UsersPane extends Stage {
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
    private List<User> users;

    private ProgressSpinnerCallbacks progressSpinnerCallbacks;

    public UsersPane(final Stage owner, ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        this.progressSpinnerCallbacks = progressSpinnerCallbacks;
        root = new BorderPane();
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        new LoadUsers(progressSpinnerCallbacks).start();
    }

    public void showDialog() {
        Text conferenceUsersText = new Text("Conference Users");
        conferenceUsersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        // populateTable();
        // root.setCenter(table);

        setScene(scene);
        show();
    }

    /**
     * Populates the users table.
     */
    private void populateTable() {

    }

    private class LoadUsers extends ProgressSpinnerService {

        /**
         * Creates a new LoadDataService.
         * 
         * @param progressSpinnerCallbacks
         *            Spinner that spins during database query.
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
                        users = UserManager.getUsers();
                        setSuccess(true);
                    } catch (Exception e) {
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
                showDialog();
            }
            super.succeeded();
        }
    }

}
