
package view.util;

/**
 * Interface responsible for changing the center pane in the MainPane pane.
 * 
 * @author Tim Mikeladze
 * @version 11-25-2013
 */
public interface MainPaneCallbacks {
    
    /**
     * Changes the center pane in MainPane.
     * 
     * @param pane The generic pane to change to
     */
    public void changeCenterPane(final GenericPane<?> pane);
}