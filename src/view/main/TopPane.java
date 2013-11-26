
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
import model.conferences.CreateConferencePane;
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
 */
public class TopPane extends GenericPane<StackPane> implements EventHandler<ActionEvent>,
        ProgressSpinnerCallbacks {
    
    private HBox leftBox;
    private HBox rightBox;
    private Button logoutButton;
    private Label welcomeLabel;
    private CustomProgressIndicator progressSpinner;
    private Button createConferenceButton;
    private Button homeButton;
    private Button backButton;
    private Button viewConferencesButton;
    
    /**.
     * 
     * Instantiates a new top pane.
     *
     * @param callbacks the callbacks
     */
    public TopPane(final MainPaneCallbacks mainPaneCallbacks, final Callbacks callbacks) {
        super(new StackPane(), callbacks);
        addMainPaneCallBacks(mainPaneCallbacks);
        
        pane.setPadding(new Insets(5));
        leftBox = new HBox(12);
        rightBox = new HBox(12);
        
        progressSpinner = new CustomProgressIndicator();
        progressSpinner.setVisible(false);
        
        create();
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
        backButton.setDisable(true);
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
        if (event.getSource() == createConferenceButton) {
            mainPaneCallbacks.changeCenterPane(new CreateConferencePane(callbacks,
                    mainPaneCallbacks, this));
            mainPaneCallbacks.update();
        }
        if (event.getSource() == logoutButton) {
            LoggedUser.getInstance().logout();
            callbacks.changeScene(new LoginPane());
        }
        if (event.getSource() == homeButton) {
            //callbacks.changeScene(new HomePane());
        }
        if (event.getSource() == backButton) {
            //callbacks.changeScene(sceneStack.pop());
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
    
    /**
     * @deprecated dont need this anymore
     * @param visible
     */
    @Deprecated
    public void setCreateConferenceButtonVisible(final boolean visible) {
        createConferenceButton.setVisible(visible);
    }
    
    public void enableBackButton(final boolean enabled) {
        backButton.setDisable(!enabled);
    }
    
}
