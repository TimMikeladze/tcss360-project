
package view.util;

/**
 * Interface responsible for changing the center pane in the MainPane pane.
 *
 * @author Tim Mikeladze
 * @version 11-25-2013
 */
public interface MainPaneCallbacks  {

    public void pushPane(GenericPane<?> pane);

    public GenericPane<?> popPane();

    public GenericPane<?> clearPanes();
}