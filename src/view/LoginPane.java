/*
 * Mohammad Juma
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 11, 2013
 */

package view;

import java.sql.SQLException;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.login.Login;

/**
 * JavaFX pane for displaying the login window.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class LoginPane extends GridPane implements EventHandler {

	private Callbacks callbacks;

	/**
	 * Text component for showing the welcome text.
	 */
	private Text welcomeText;

	/**
	 * Label for showing the email label text.
	 */
	private Label emailLabel;

	/**
	 * TextField for inputing the email.
	 */
	private TextField emailTextField;

	/**
	 * Label for showing the password label text.
	 */
	private Label passwordLabel;

	/**
	 * PasswordField for inputing the password.
	 */
	private PasswordField passwordField;

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
	private Text signInText;

	/**
	 * Constructs a new LoginPane pane that extends GridPane and displays a
	 * prompt for the user to login or register.
	 */
	public LoginPane() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		create();
	}

	public void addCallbacks(Callbacks callbacks) {
		this.callbacks = callbacks;
	}

	/**
	 * Creates the main components of the LoginPane pane.
	 */
	private void create() {
		welcomeText = new Text("Welcome");
		welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.add(welcomeText, 0, 0, 2, 1);

		emailLabel = new Label("Email:");
		this.add(emailLabel, 0, 1);

		emailTextField = new TextField();
		this.add(emailTextField, 1, 1);

		passwordLabel = new Label("Password:");
		this.add(passwordLabel, 0, 2);

		passwordField = new PasswordField();
		this.add(passwordField, 1, 2);

		signInButton = new Button("Sign in");
		signInButton.setOnAction(this);

		registerButton = new Button("Register");
		registerButton.setOnAction(this);

		final HBox buttonHBox = new HBox(10);
		buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
		buttonHBox.getChildren().add(signInButton);
		buttonHBox.getChildren().add(registerButton);
		this.add(buttonHBox, 1, 4);

		signInText = new Text();
		this.add(signInText, 1, 6);
	}

	/**
	 * 
	 * @param event
	 */
	@Override
	public void handle(Event event) {
		if (event.getSource() == signInButton) {
			try {
				Login.loginUser(emailTextField.getText().trim());
				if (callbacks != null) {
					callbacks.changeScene(new MainPane());
				}
			} catch (SQLException e) {
				signInText.setFill(Color.FIREBRICK);
				signInText.setText(e.getMessage());
			}
		}

		if (event.getSource() == registerButton) {

		}

	}
}