
package view.util;

import javafx.scene.layout.Pane;

/**
 * A GenericPane for handling callbacks.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public abstract class GenericPane<T extends Pane> {
    
    /**
     * A callback to the scene this pane is in.
     */
    protected SceneCallbacks sceneCallback;
    
    /**
     * Progress spinner callback.
     */
    protected ProgressSpinnerCallbacks progressSpinnerCallback;
    
    /**
     * The pane.
     */
    protected T pane;
    
    /**
     * A callback for the center pane.
     */
    protected CenterPaneCallbacks centerPaneCallback;
    
    /**
     * Creates a new Generic Pane.
     * 
     * @param t the pane.
     */
    public GenericPane(final T t) {
        this.pane = t;
    }
    
    /**
     * Creates a new Generic Pane.
     * 
     * @param t the pane.
     */
    public GenericPane(final T t, final SceneCallbacks sceneCallback) {
        this.pane = t;
        this.sceneCallback = sceneCallback;
    }
    
    /**
     * Adds a callback to the scene.
     * 
     * @param sceneCallback the callback
     */
    public void addSceneCallback(final SceneCallbacks sceneCallback) {
        this.sceneCallback = sceneCallback;
    }
    
    /**
     * Adds a callback to the progress spinner.
     * 
     * @param progressSpinnerCallbacks the callback
     */
    public void addProgressSpinnerCallBack(final ProgressSpinnerCallbacks progressSpinnerCallback) {
        this.progressSpinnerCallback = progressSpinnerCallback;
    }
    
    /**
     * Adds a callback to the center pane.
     * 
     * @param centerPaneCallback the callback
     */
    public void addCenterPaneCallBacks(final CenterPaneCallbacks centerPaneCallback) {
        this.centerPaneCallback = centerPaneCallback;
    }
    
    /**
     * Gets the Generic Pane
     * 
     * @return the Generic Pane.
     */
    public T getPane() {
        return pane;
    }
    
    /**
     * Returns the refreshed pane.
     * 
     * @return The refreshed pane.
     */
    public abstract GenericPane<T> refresh();
}
