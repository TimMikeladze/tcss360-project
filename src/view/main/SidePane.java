
package view.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import view.util.GenericPane;
import view.util.SceneCallbacks;

/**
 * TODO: Remove this class as it has been deprecated.
 * Creates a pane that functions as the navigation bar for the application and runs along the
 * windows left border.
 */
public class SidePane extends GenericPane<TilePane> {
    
    /**
     * Instantiates a new side pane.
     * 
     * @param callbacks the callbacks
     */
    public SidePane(final SceneCallbacks callbacks) {
        super(new TilePane(), callbacks);
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setAlignment(Pos.TOP_LEFT);
        create();
    }
    
    @Override
    public GenericPane<TilePane> refresh() {
        return new SidePane(sceneCallback);
    }
    
    /**
     * Creates the side pane.
     */
    private void create() {
        Button homeButton = new Button("Home");
        homeButton.setMaxWidth(Double.MAX_VALUE);
        
        Button conferencesButton = new Button("View Conferences");
        conferencesButton.setMaxWidth(Double.MAX_VALUE);
        
        Button submissionsButton = new Button("View Submissions");
        submissionsButton.setMaxWidth(Double.MAX_VALUE);
        
        Button usersButton = new Button("Users");
        usersButton.setMaxWidth(Double.MAX_VALUE);
        
        pane.getChildren().addAll(homeButton, conferencesButton, submissionsButton, usersButton);
    }
}
