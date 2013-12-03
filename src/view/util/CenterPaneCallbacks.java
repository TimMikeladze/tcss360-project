
package view.util;

/**
 * Interface responsible for changing the center pane in the MainPane pane.
 *
 * @author Tim Mikeladze
 * @version 11-25-2013
 */
public interface CenterPaneCallbacks  {

    /**
     * Pushes the pane onto the center pane stack.
     * 
     * @param pane The pane to push onto the stack.
     */
    public void pushPane(GenericPane<?> pane);

    /**
     * Pops the pane off of the center pane stack.
     * 
     * @return The popped pane.
     */
    public GenericPane<?> popPane();

    /**
     * Clears all panes from the center pane stack.
     * 
     * @return TODO what does the returned pane, GenericPane<?>, represent?
     */
    public GenericPane<?> clearPanes();
}