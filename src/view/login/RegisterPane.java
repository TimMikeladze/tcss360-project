
package view.login;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.database.DatabaseException;
import model.login.Login;
import view.util.GenericPane;
import view.util.ProgressService;
import view.util.StatusText;
import view.util.Validator;

/**
 * JavaFX pane for displaying the registration window.
 * 
 * @author Cathryn Castillo
 * @version 11-11-2013
 */
public class RegisterPane extends GenericPane<GridPane> implements EventHandler<ActionEvent> {
    
    /**
     * TextField for the users first name.
     */
    private TextField firstNameTextField;
    
    /**
     * TextField for the users last name.
     */
    private TextField lastNameTextField;
    
    /**
     * TextField for the users email.
     */
    private TextField emailTextField;
    
    /**
     * Button for registering the user.
     */
    private Button registerButton;
    
    /**
     * Button returning the user to the login page.
     */
    private Button returnButton;
    
    /**
     * Status text for registration errors.
     */
    private StatusText registrationText;
    
    /**
     * Constructs a new RegisterPane pane that extends GridPane and allows a new user to
     * register.
     */
    public RegisterPane() {
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
        return new RegisterPane();
    }
    
    /**
     * Creates the main components of the RegisterPane pane.
     */
    private void create() {
        final Text scenetitle = new Text("Registration");
        scenetitle.setId("header1");
        pane.add(scenetitle, 0, 0);
        
        final Label firstName = new Label("First Name:");
        pane.add(firstName, 0, 1);
        firstNameTextField = new TextField();
        pane.add(firstNameTextField, 1, 1);
        
        final Label lastName = new Label("Last Name:");
        pane.add(lastName, 0, 2);
        lastNameTextField = new TextField();
        pane.add(lastNameTextField, 1, 2);
        
        final Label email = new Label("Email:");
        pane.add(email, 0, 3);
        emailTextField = new TextField();
        pane.add(emailTextField, 1, 3);
        
        registerButton = new Button("Register");
        registerButton.setOnAction(this);
        returnButton = new Button("Go Back");
        returnButton.setOnAction(this);
        
        final HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHBox.getChildren().add(returnButton);
        buttonHBox.getChildren().add(registerButton);
        pane.add(buttonHBox, 1, 5);
        
        registrationText = new StatusText();
        pane.add(registrationText, 1, 6);
    }
    
    /**
     * Event handler for handling user input.
     * 
     * @param event The event that occurred
     */
    @Override
    public void handle(final ActionEvent event) {
        final String firstName = firstNameTextField.getText();
        final String lastName = lastNameTextField.getText();
        final String email = emailTextField.getText();
        
        if (event.getSource() == registerButton) {
            if (Validator.isEmpty(firstName, lastName, email)) {
                registrationText.setErrorText("Missing required fields");
            }
            else if (!Validator.isValidEmail(email)) {
                registrationText.setErrorText("Not a valid email");
            }
            else {
                new RegistrationService(sceneCallback.getPrimaryStage(), firstName, lastName, email).start();
            }
        }
        else if (event.getSource() == returnButton) {
            sceneCallback.changeScene(new LoginPane());
        }
    }
    
    /**
     * Inner class for handling user registration with the database through the use of a seperate thread.
     * 
     * @author Tim Mikeladze
     * @version 11-20-2013
     */
    private class RegistrationService extends ProgressService {
        
        /**
         * The users first name.
         */
        private String firstName;
        
        /**
         * The users last name.
         */
        private String lastName;
        
        /**
         * The users email.
         */
        private String email;
        
        /**
         * Populates the class with the required information required to register.
         * 
         * @param primaryStage The stage this pane is in.
         * @param firstName The users first name.
         * @param lastName The users last name.
         * @param email The users email.
         */
        public RegistrationService(final Stage primaryStage, final String firstName, final String lastName,
                final String email) {
            super(primaryStage);
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
        
        /**
         * Creates a new task for registration.
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
                        Login.registerUser(firstName, lastName, email);
                        setSuccess(true);
                    }
                    catch (DatabaseException e) {
                        registrationText.setErrorText(e.getMessage());
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when registration is successful.
         */
        @Override
        protected void succeeded() {
            super.succeeded();
            if (getSuccess()) {
                sceneCallback.changeScene(new LoginPane());
            }
        }
    }
}