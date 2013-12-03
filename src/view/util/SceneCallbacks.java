
package view.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Handles JavaFX GUI scene Callbacks for changing the entire scene of the window.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public interface SceneCallbacks {
    
    /**
     * Changes the scene in the window.
     * 
     * @param genericPane The pane to change to
     */
    public void changeScene(final GenericPane<?> genericPane);
    
    /**
     * Changes the scene in the window.
     * 
     * @param genericPane The pane to change to
     * @param width the width of the new pane
     * @param height the height of the new pane
     */
    public void changeScene(final GenericPane<?> genericPane, final int width, final int height);
    
    /**
     * Gets the scene
     * 
     * @return the scene
     */
    public Scene getScene();
    
    /**
     * Gets the stage
     * 
     * @return the stage
     */
    public Stage getPrimaryStage();
}
