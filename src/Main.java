/*
 * Mohammad Juma, Tim Mikeladze, Srdjan Stojcic, Cathryn Castillo, Jordan Matthews
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 11, 2013
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.login.LoginPane;
import view.util.Callbacks;
import view.util.GenericPane;

/**
 * Begins the program by instantiating and starting the JavaFX GUI.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
 * @version 11-11-2013
 */
public class Main extends Application implements Callbacks {
    
    /**
     * The default width of the application.
     */
    private static final int DEFAULT_WIDTH = 950;
    
    /**
     * The default height of the application.
     */
    private static final int DEFAULT_HEIGHT = 600;
    
    /**
     * The main JAvaFX scene of the application.
     */
    private Scene scene;
    
    /**
     * The main JavaFX stage of the application.
     */
    private Stage primaryStage;
    
    /**
     * Used primarily within the IDE to launch the JavaFX GUI. Note: This method is not required
     * for JavaFX applications as the JavaFX Packager Tool embeds the JavaFX Launcher in the
     * final JAR file.
     * 
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }
    
    /**
     * Overridden start() method of JavaFX Application.
     * 
     * @param primaryStage The main stage that holds this applications GUI.
     */
    @Override
    public void start(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        primaryStage.setTitle("TCSS360 Project");
        
        LoginPane loginPane = new LoginPane();
        loginPane.addCallbacks(this);
        scene = new Scene(loginPane.getPane(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scene.getStylesheets()
             .add("view/styling/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Overridden changeScene() method of JavaFX Application.
     * 
     * @param genericPane The JavaFX pane the scene will change to.
     */
    @Override
    public void changeScene(final GenericPane<?> genericPane) {
        changeScene(genericPane, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    /**
     * Overridden changeScene() method of JavaFX Application.
     * 
     * @param genericPane The JavaFX pane the scene will change to.
     * @param width The width of the pane.
     * @param heigth The height of the pane.
     */
    @Override
    public void changeScene(final GenericPane<?> genericPane, final int width, final int height) {
        genericPane.addCallbacks(this);
        scene = new Scene(genericPane.getPane(), width, height);
        scene.getStylesheets()
             .add("view/styling/styles.css");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Overridden getScene() method of JavaFX Application.
     */
    @Override
    public Scene getScene() {
        return scene;
    }
    
    /**
     * Overridden getPrimaryStage() method of JavaFX Application.
     */
    @Override
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
}