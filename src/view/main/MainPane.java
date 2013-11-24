
package view.main;

import model.conferences.Conference;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import view.main.conferences.ConferencePane;
import view.util.GenericPane;

/**
 * Main JavaFX pane for holding the applications UI upon successful login.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class MainPane extends GenericPane<BorderPane> {
    
    /**
     * Constructs a new MainPane pane that extends BorderPane and displays the main user
     * interface that all other panes are placed upon.
     */
    public MainPane() {
        super(new BorderPane());
        pane.setTop(new TopPane().getPane());
        pane.setLeft(new SidePane().getPane());
        pane.setCenter(centerPane());
    }
    
    /**
     * Creates a pane for displaying the main interface of the program.
     * 
     * @return stackPane The center pane of MainPane's BorderPane.
     */
    private StackPane centerPane() {
        final StackPane stackPane = new StackPane();
        
        // stackPane.getStylesheets().add("style.css");
        //stackPane.getChildren().add(new ConferencePane(Conference.conferenceFromID(151)).getPane());
        stackPane.getChildren().add(new HomePane().getPane());
        
        return stackPane;
    }
}