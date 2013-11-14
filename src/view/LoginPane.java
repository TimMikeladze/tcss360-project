/*
 * Mohammad Juma
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 11, 2013
 */

package view;

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
 * JavaFX pane for displaying the login window.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class LoginPane extends GridPane {

    /**
     * Constructs a new LoginPane pane that extends GridPane and displays a prompt
     * for the user to login or register.
     */
    public LoginPane() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        create();
    }
    
    /**
     * Creates the main components of the LoginPane pane.
     */
    private void create() {
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.add(scenetitle, 0, 0, 2, 1);

        Label email = new Label("Email:");
        this.add(email, 0, 1);

        TextField emailTextField = new TextField();
        this.add(emailTextField, 1, 1);

        Label password = new Label("Password:");
        this.add(password, 0, 2);

        PasswordField passwordField = new PasswordField();
        this.add(passwordField, 1, 2);
        
        Button signInButton = new Button("Sign in");
        Button registerButton = new Button("Register");
        HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHBox.getChildren().add(signInButton);
        buttonHBox.getChildren().add(registerButton);
        this.add(buttonHBox, 1, 4);
        
        final Text signInText = new Text();
        this.add(signInText, 1, 6);
        
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                signInText.setFill(Color.FIREBRICK);
                signInText.setText("Sign in button pressed");
            }
        });
    }
}