
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
     * The pane.
     */
    protected T pane;
    
    /**
     * Creates a new Generic Pane.
     * 
     * @param t the pane.
     */
    public GenericPane(final T t) {
        this.pane = t;
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
     * Gets the Generic Pane
     * 
     * @return the Generic Pane.
     */
    public T getPane() {
        return pane;
    }
}
