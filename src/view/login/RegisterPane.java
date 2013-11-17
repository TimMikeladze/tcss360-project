/*
 * Mohammad Juma
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 11, 2013
 */

package view.login;

import javafx.event.ActionEvent;
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

/**
 * JavaFX pane for displaying the registration window.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class RegisterPane extends GridPane {

	/**
	 * Constructs a new RegisterPane pane that extends GridPane and allows a new
	 * user to register.
	 */
	public RegisterPane() {
		this.setAlignment(Pos.TOP_LEFT);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		create();
	}

	/**
	 * Creates the main components of the RegisterPane pane.
	 */
	private void create() {
		Text scenetitle = new Text("Registration");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.add(scenetitle, 0, 0);

		Label firstName = new Label("First Name:");
		this.add(firstName, 0, 1);

		TextField firstNameTextField = new TextField();
		this.add(firstNameTextField, 1, 1);

		Label lastName = new Label("Last Name:");
		this.add(lastName, 0, 2);

		TextField lastNameTextField = new TextField();
		this.add(lastNameTextField, 1, 2);

		Label email = new Label("Email:");
		this.add(email, 0, 3);

		TextField emailTextField = new TextField();
		this.add(emailTextField, 1, 3);

		Label password = new Label("Password:");
		this.add(password, 0, 4);

		PasswordField passwordField = new PasswordField();
		this.add(passwordField, 1, 4);

		Button registerButton = new Button("Register");
		Button returnButton = new Button("Go Back");
		HBox buttonHBox = new HBox(10);
		buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
		buttonHBox.getChildren().add(registerButton);
		buttonHBox.getChildren().add(returnButton);
		this.add(buttonHBox, 1, 5);

		final Text registrationText = new Text();
		this.add(registrationText, 1, 6);

		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				registrationText.setFill(Color.FIREBRICK);
				registrationText.setText("Sign in button pressed");
			}
		});
	}
}