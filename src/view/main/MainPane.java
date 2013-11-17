/*
 * Mohammad Juma
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 11, 2013
 */

package view.main;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import model.conferences.ConferenceManager;
import model.permissions.Permissions;
import view.home.HomePane;
import view.util.GenericPane;

/**
 * Main JavaFX pane for holding the applications UI upon successful login.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class MainPane extends GenericPane<BorderPane> {

	/**
	 * Constructs a new MainPane pane that extends BorderPane and displays the
	 * main user interface that all other panes are placed upon.
	 */
	public MainPane() {
		super(new BorderPane());
		pane.setTop(topPane());
		pane.setLeft(leftPane());
		pane.setCenter(centerPane());
	}

	/**
	 * Creates a pane that runs horizontal along the windows north border and
	 * displays the users name along with the logout button.
	 * 
	 * @return stackPane The top pane of MainPane's BorderPane.
	 */
	private StackPane topPane() {
		final StackPane stackPane = new StackPane();
		stackPane.setPadding(new Insets(5, 5, 5, 5));

		final HBox leftBox = new HBox(10);
		leftBox.setAlignment(Pos.CENTER_LEFT);
		Label welcomeLabel = new Label("Welcome User");
		leftBox.getChildren().add(welcomeLabel);

		final HBox rightBox = new HBox(10);
		rightBox.setAlignment(Pos.CENTER_RIGHT);
		Button logoutButton = new Button("Logout");
		rightBox.getChildren().add(logoutButton);

		stackPane.getChildren().add(leftBox);
		stackPane.getChildren().add(rightBox);

		return stackPane;
	}

	/**
	 * Creates a pane that functions as the navigation bar for the application
	 * and runs along the windows left border.
	 * 
	 * @return tilePane The left pane of MainPane's BorderPane.
	 */
	private TilePane leftPane() {
		final TilePane tilePane = new TilePane(Orientation.VERTICAL);
		tilePane.setPadding(new Insets(5, 5, 5, 5));
		tilePane.setAlignment(Pos.TOP_LEFT);

		Button homeButton = new Button("Home");
		homeButton.setMaxWidth(Double.MAX_VALUE);

		Button conferencesButton = new Button("View Conferences");
		conferencesButton.setMaxWidth(Double.MAX_VALUE);

		Button submissionsButton = new Button("View Submissions");
		submissionsButton.setMaxWidth(Double.MAX_VALUE);

		Button usersButton = new Button("Users");
		usersButton.setMaxWidth(Double.MAX_VALUE);

		tilePane.getChildren().addAll(homeButton, conferencesButton,
				submissionsButton, usersButton);

		// Example of how the Permissions class works
		if (Permissions.hasPermission(ConferenceManager.class,
				"getUsersInConference", Permissions.ADMIN)) {
			Button messagesButton = new Button("Messages");
			messagesButton.setMaxWidth(Double.MAX_VALUE);
			tilePane.getChildren().add(messagesButton);
		}

		return tilePane;
	}

	/**
	 * Creates a pane for displaying the main interface of the program.
	 * 
	 * @return stackPane The center pane of MainPane's BorderPane.
	 */
	private StackPane centerPane() {
		final StackPane stackPane = new StackPane();

		// stackPane.getStylesheets().add("style.css");
		stackPane.getChildren().add(new HomePane().getPane());

		return stackPane;
	}
}