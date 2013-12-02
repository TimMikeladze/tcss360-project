
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
     * The callback.
     */
    protected Callbacks callbacks;
    
    /**
     * Progress spinner callbacks.
     */
    protected ProgressSpinnerCallbacks progressSpinnerCallbacks;
    
    /**
     * The pane.
     */
    protected T pane;

    /**
     * Main Pane callbacks.
     */
    protected MainPaneCallbacks mainPaneCallbacks;
    
    /**
     * Returns the refreshed pane.
     * 
     * @return The refreshed pane.
     */
    public abstract GenericPane<T> refresh();
    
    
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
    public GenericPane(final T t, final Callbacks callbacks) {
        this.pane = t;
        this.callbacks = callbacks;
    }
    
    /**
     * Adds a callback.
     * 
     * @param callbacks the callback
     */
    public void addCallbacks(final Callbacks callbacks) {
        this.callbacks = callbacks;
    }
    
    /**
     * Add progress spinner callback.
     * 
     * @param progressSpinnerCallbacks
     */
    public void addProgressSpinnerCallBacks(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        this.progressSpinnerCallbacks = progressSpinnerCallbacks;
    }
    
    /**
     * Adds Main Pane callback.
     * 
     * @param progressSpinnerCallbacks
     */
    public void addMainPaneCallBacks(final MainPaneCallbacks mainPaneCallbacks) {
        this.mainPaneCallbacks = mainPaneCallbacks;
    }
    
    /**
     * Gets the Generic Pane
     * 
     * @return the Generic Pane.
     */
    public T getPane() {
        return pane;
    }
}
