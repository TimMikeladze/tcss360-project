/*
 * Mohammad Juma
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 11, 2013
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Callbacks;
import view.GenericPane;
import view.LoginPane;

/**
 * Begins the program by instantiating and starting the JavaFX GUI.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class Main extends Application implements Callbacks {

	private static final int DEFAULT_WIDTH = 950;
	private static final int DEFAULT_HEIGHT = 600;
	/**
	 * Private constructor, to prevent instantiation of this class.
	 * 
	 * private Main() { // Does nothing. }
	 */

	private Scene scene;
	private Stage primaryStage;

	/**
	 * Used primarily within the IDE to launch the JavaFX GUI. Note: This method
	 * is not required for JavaFX applications as the JavaFX Packager Tool
	 * embeds the JavaFX Launcher in the final JAR file.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * @param primaryStage
	 *            The main stage that holds this applications GUI.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		primaryStage.setTitle("TCSS360 Project");

		LoginPane loginPane = new LoginPane();
		loginPane.addCallbacks(this);
		scene = new Scene(loginPane.getPane(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	@Override
	public void changeScene(GenericPane<?> genericPane) {
		changeScene(genericPane, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public void changeScene(GenericPane<?> genericPane, int width, int height) {
		genericPane.addCallbacks(this);
		scene = new Scene(genericPane.getPane(), width, height);
		primaryStage.setScene(scene);

		primaryStage.show();

	}
}