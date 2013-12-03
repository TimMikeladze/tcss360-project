
package view.login;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.database.DatabaseException;
import model.login.Login;
import model.users.User;
import view.main.MainPane;
import view.util.CustomProgressIndicator;
import view.util.GenericPane;
import view.util.StatusText;
import view.util.Validator;
import controller.user.LoggedUser;

/**
 * JavaFX pane for displaying the login window.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class LoginPane extends GenericPane<GridPane> implements EventHandler {
    
    /**
     * TextField for inputing the email.
     */
    private TextField emailTextField;
    
    /**
     * Button for signing into the application.
     */
    private Button signInButton;
    
    /**
     * Button for registering for an account.
     */
    private Button registerButton;
    
    /**
     * Text for displaying sign in errors.
     */
    private StatusText signInText;
    
    /**
     * Progress indicator to show during login process.
     */
    private CustomProgressIndicator progressIndicator;
    
    /**
     * Constructs a new LoginPane pane that extends GridPane and displays a prompt for the user
     * to login or register.
     */
    public LoginPane() {
        super(new GridPane());
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        
        create();
    }
    
    /**
     * Refreshes the current pane after data has been changed.
     */
    @Override
    public GenericPane<GridPane> refresh() {
        return new LoginPane();
    }
    
    /**
     * Creates the main components of the LoginPane pane.
     */
    private void create() {
        final Text welcomeText = new Text("Welcome");
        welcomeText.setId("header1");
        pane.add(welcomeText, 0, 0, 2, 1);
        
        final Label emailLabel = new Label("Email:");
        pane.add(emailLabel, 0, 1);
        
        emailTextField = new TextField();
        emailTextField.setOnKeyReleased(this);
        pane.add(emailTextField, 1, 1);
        
        signInButton = new Button("Sign in");
        signInButton.setOnAction(this);
        
        registerButton = new Button("Register");
        registerButton.setOnAction(this);
        
        final HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHBox.getChildren().add(signInButton);
        buttonHBox.getChildren().add(registerButton);
        pane.add(buttonHBox, 1, 4);
        
        signInText = new StatusText();
        pane.add(signInText, 1, 6);
        
        progressIndicator = new CustomProgressIndicator();
        pane.add(progressIndicator, 1, 1);
        
        //TODO remove this when done
        emailTextField.setText("srdjan@email.com");
        login();
        // End here
    }
    
    /**
     * Sets the progress indicator visible.
     * 
     * @param show the new progress indicator visible
     */
    private void setProgressIndicatorVisible(final boolean show) {
        for (Node node : pane.getChildren()) {
            if (node instanceof ProgressIndicator) {
                node.setVisible(show);
            }
            else {
                node.setVisible(!show);
            }
        }
    }
    
    /**
     * Handles action events from the user.
     * 
     * @param event the event
     */
    @Override
    public void handle(final Event event) {
        signInText.clear();
        if (event.getSource() == emailTextField) {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode() == KeyCode.ENTER) {
                login();
            }
        }
        else if (event.getSource() == signInButton) {
            login();
        }
        else if (event.getSource() == registerButton) {
            sceneCallback.changeScene(new RegisterPane());
        }
    }
    
    /**
     * Validates fields and logs in.
     */
    private void login() {
        final String email = emailTextField.getText().trim();
        if (email.isEmpty()) {
            signInText.setErrorText("Forgot to enter an email");
        }
        else if (!Validator.isValidEmail(email)) {
            signInText.setErrorText("Not a valid email");
        }
        else {
            new LoginService().start();
        }
    }
    
    /**
     * Private inner class for logging into the application.
     * 
     * @author Tim Mikeladze
     * @version 11-17-2013
     */
    private class LoginService extends Service<String> {
        
        /**
         * Boolean for tracking a successful login.
         */
        private boolean success;
        
        /**
         * Starts a new task for logging in.
         * 
         * @return the task
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
                        User user = Login.loginUser(emailTextField.getText().trim());
                        LoggedUser.getInstance().setUser(user);
                        success = true;
                    }
                    catch (DatabaseException e) {
                        signInText.setErrorText(e.getMessage());
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when the operation is cancelled.
         */
        @Override
        protected void cancelled() {
            setProgressIndicatorVisible(false);
            super.cancelled();
        }
        
        /**
         * Executes the progress spinner.
         */
        @Override
        protected void executeTask(final Task<String> task) {
            progressIndicator.progressProperty().bind(task.progressProperty());
            progressIndicator.setStyle(" -fx-progress-color: gold;");
            setProgressIndicatorVisible(true);
            super.executeTask(task);
        }
        
        /**
         * Called when the operation fails.
         */
        @Override
        protected void failed() {
            setProgressIndicatorVisible(false);
            super.failed();
        }
        
        /**
         * Called when the operation succeeds.
         */
        @Override
        protected void succeeded() {
            super.succeeded();
            setProgressIndicatorVisible(false);
            if (success) {
                sceneCallback.changeScene(new MainPane(sceneCallback));
            }
        }
    }
}