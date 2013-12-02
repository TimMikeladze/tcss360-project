
package view.main;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import view.conferences.ConferencesPane;
import view.login.LoginPane;
import view.util.Callbacks;
import view.util.CustomProgressIndicator;
import view.util.GenericPane;
import view.util.MainPaneCallbacks;
import view.util.ProgressSpinnerCallbacks;
import controller.user.LoggedUser;

/**
 * Creates a pane that runs horizontal along the windows north border and displays the users
 * name along with the logout button.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
 * @version 11-11-2013
 */
public class TopPane extends GenericPane<StackPane> implements EventHandler<ActionEvent>,
        ProgressSpinnerCallbacks {
    
    /**
     * The left pane holding the welcome message and the users name.
     */
    private HBox leftBox;
    
    /**
     * The right box holding the static buttons for the application.
     */
    private HBox rightBox;
    
    /**
     * The logout button to logout of the application.
     */
    private Button logoutButton;
    
    /**
     * The welcome label showing the users name.
     */
    private Label welcomeLabel;
    
    /**
     * The progress indicator, displayed when the database is being queried.
     */
    private CustomProgressIndicator progressSpinner;
    
    /**
     * Home button for returning to the HomePane.
     */
    private Button homeButton;
    
    /**
     * Back button for returning to the previous screen.
     */
    private Button backButton;
    
    /**
     * View conferences button for viewing conferences.
     */
    private Button viewConferencesButton;
    
    /**
     * Instantiates a new top pane.
     * 
     * @param mainPaneCallbacks
     * @param
     */
    public TopPane(final MainPaneCallbacks mainPaneCallbacks, final Callbacks callbacks) {
        super(new StackPane(), callbacks);
        addMainPaneCallBacks(mainPaneCallbacks);
        
        pane.setPadding(new Insets(5));
        leftBox = new HBox(12);
        rightBox = new HBox(12);
        
        progressSpinner = new CustomProgressIndicator();
        progressSpinner.setStyle(" -fx-progress-color: gold;");
        progressSpinner.setVisible(false);
        
        create();
    }
    
    @Override
    public GenericPane<StackPane> refresh() {
        return new TopPane(mainPaneCallbacks, callbacks);
    }
    
    /**
     * Creates the top pane.
     */
    private void create() {
        leftBox.setAlignment(Pos.CENTER_LEFT);
        welcomeLabel = new Label("Welcome: " + LoggedUser.getInstance().getUser().getFullName());
        welcomeLabel.setFont(Font.font(welcomeLabel.getFont().getName(), FontWeight.BOLD, 12));
        leftBox.getChildren().add(welcomeLabel);
        
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        
        rightBox.getChildren().add(progressSpinner);
        
        homeButton = new Button("Home");
        homeButton.setVisible(true);
        homeButton.setOnAction(this);
        rightBox.getChildren().add(homeButton);
        
        backButton = new Button("Back");
        backButton.setVisible(true);
        backButton.setDisable(false);
        backButton.setOnAction(this);
        rightBox.getChildren().add(backButton);
        
        viewConferencesButton = new Button("View Conferences");
        viewConferencesButton.setVisible(true);
        viewConferencesButton.setDisable(false);
        viewConferencesButton.setOnAction(this);
        rightBox.getChildren().add(viewConferencesButton);
        
        logoutButton = new Button("Logout");
        logoutButton.setOnAction(this);
        rightBox.getChildren().add(logoutButton);
        
        pane.getChildren().add(leftBox);
        pane.getChildren().add(rightBox);
    }
    
    @Override
    public void handle(final ActionEvent event) {
        Object source = event.getSource();
        if (source == logoutButton) {
            LoggedUser.getInstance().logout();
            mainPaneCallbacks.clearPanes();
            callbacks.changeScene(new LoginPane());
        }
        else if (source == homeButton) {
            mainPaneCallbacks.clearPanes();
        }
        else if (source == viewConferencesButton) {
            mainPaneCallbacks.pushPane(new ConferencesPane(callbacks, mainPaneCallbacks, this));
        }
        else if (source == backButton) {
            mainPaneCallbacks.popPane();
        }
        
    }
    
    @Override
    public void stop() {
        progressSpinner.setVisible(false);
    }
    
    @Override
    public void start() {
        progressSpinner.setVisible(true);
    }
    
    @Override
    public void bindTask(final Task<?> task) {
        progressSpinner.progressProperty().bind(task.progressProperty());
    }
    
    public void enableBackButton(final boolean enabled) {
        backButton.setDisable(!enabled);
    }
}
