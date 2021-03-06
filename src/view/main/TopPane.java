
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
import view.util.CenterPaneCallbacks;
import view.util.CustomProgressIndicator;
import view.util.GenericPane;
import view.util.ProgressSpinnerCallbacks;
import view.util.SceneCallbacks;
import controller.user.LoggedUser;

/**
 * Creates a pane that runs horizontal along the windows north border and displays the users
 * name along with the logout button.
 * 
 * @author Cathryn Castillo
 * @version 11-11-2013
 */
public class TopPane extends GenericPane<StackPane> implements EventHandler<ActionEvent>, ProgressSpinnerCallbacks {
    
    /**
     * The logout button to logout of the application.
     */
    private Button logoutButton;
    
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
    public TopPane(final CenterPaneCallbacks mainPaneCallbacks, final SceneCallbacks callbacks) {
        super(new StackPane(), callbacks);
        addCenterPaneCallBacks(mainPaneCallbacks);
        
        pane.setPadding(new Insets(5));
        
        progressSpinner = new CustomProgressIndicator();
        progressSpinner.setStyle(" -fx-progress-color: gold;");
        progressSpinner.setVisible(false);
        
        create();
    }
    
    @Override
    public GenericPane<StackPane> refresh() {
        return new TopPane(centerPaneCallback, sceneCallback);
    }
    
    /**
     * Creates the top pane.
     */
    private void create() {
        final HBox leftBox = new HBox(12);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        final Label welcomeLabel = new Label("Welcome: " + LoggedUser.getInstance().getUser().getFullName());
        welcomeLabel.setFont(Font.font(welcomeLabel.getFont().getName(), FontWeight.BOLD, 12));
        leftBox.getChildren().add(welcomeLabel);
        
        final HBox rightBox = new HBox(12);
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
    
    /**
     * Event handler for handling user input.
     * 
     * @param event The event that occurred
     */
    @Override
    public void handle(final ActionEvent event) {
        Object source = event.getSource();
        if (source == logoutButton) {
            LoggedUser.getInstance().logout();
            centerPaneCallback.clearPanes();
            sceneCallback.changeScene(new LoginPane());
        }
        else if (source == homeButton) {
            centerPaneCallback.clearPanes();
        }
        else if (source == viewConferencesButton) {
            centerPaneCallback.pushPane(new ConferencesPane(sceneCallback, centerPaneCallback, this));
        }
        else if (source == backButton) {
            centerPaneCallback.popPane();
        }
        
    }
    
    /**
     * Stops the progress spinner.
     */
    @Override
    public void stop() {
        progressSpinner.setVisible(false);
    }
    
    /**
     * Starts the progress spinner.
     */
    @Override
    public void start() {
        progressSpinner.setVisible(true);
    }
    
    /**
     * Binds the progress spinner.
     */
    @Override
    public void bindTask(final Task<?> task) {
        progressSpinner.progressProperty().bind(task.progressProperty());
    }
    
    /**
     * Changes the state of the back button.
     * 
     * @param enabled true to enable
     */
    public void enableBackButton(final boolean enabled) {
        backButton.setDisable(!enabled);
    }
}
