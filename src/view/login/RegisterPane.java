
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class RegisterPane extends GenericPane<GridPane> implements EventHandler<ActionEvent> {
    
    private Text scenetitle;
    private Label firstName;
    private TextField firstNameTextField;
    private Label lastName;
    private TextField lastNameTextField;
    private TextField emailTextField;
    private Button registerButton;
    private Button returnButton;
    private StatusText registrationText;
    private Label email;
    
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
     * Creates the main components of the RegisterPane pane.
     */
    private void create() {
        scenetitle = new Text("Registration");
        scenetitle.setId("reg-title");
        pane.add(scenetitle, 0, 0);
        
        firstName = new Label("First Name:");
        pane.add(firstName, 0, 1);
        
        firstNameTextField = new TextField();
        pane.add(firstNameTextField, 1, 1);
        
        lastName = new Label("Last Name:");
        pane.add(lastName, 0, 2);
        
        lastNameTextField = new TextField();
        pane.add(lastNameTextField, 1, 2);
        
        email = new Label("Email:");
        pane.add(email, 0, 3);
        
        emailTextField = new TextField();
        pane.add(emailTextField, 1, 3);
        
        registerButton = new Button("Register");
        returnButton = new Button("Go Back");
        
        HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        
        buttonHBox.getChildren().add(returnButton);
        
        buttonHBox.getChildren().add(registerButton);
        
        pane.add(buttonHBox, 1, 5);
        
        registrationText = new StatusText();
        pane.add(registrationText, 1, 6);
        
        registerButton.setOnAction(this);
        returnButton.setOnAction(this);
    }
    
    @Override
    public void handle(final ActionEvent event) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        
        if (event.getSource() == registerButton) {
            if (Validator.isEmpty(firstName, lastName, email)) {
                registrationText.setErrorText("Missing required fields");
            }
            else if (!Validator.isValidEmail(email)) {
                registrationText.setErrorText("Not a valid email");
            }
            else {
                new RegistrationService(callbacks.getPrimaryStage(), firstName, lastName, email)
                        .start();
            }
        }
        else if (event.getSource() == returnButton) {
            callbacks.changeScene(new LoginPane());
        }
    }
    
    private class RegistrationService extends ProgressService {
        
        private String firstName;
        private String lastName;
        private String email;
        
        public RegistrationService(final Stage primaryStage, final String firstName,
                final String lastName, final String email) {
            super(primaryStage);
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
        
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                
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
        
        @Override
        protected void succeeded() {
            super.succeeded();
            if (getSuccess()) {
                callbacks.changeScene(new LoginPane());
            }
        }
    }
}