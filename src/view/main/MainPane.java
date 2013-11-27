
package view.main;

import javafx.scene.layout.BorderPane;
import view.home.HomePane;
import view.util.Callbacks;
import view.util.GenericPane;
import view.util.MainPaneCallbacks;

/**
 * Main JavaFX pane for holding the applications UI upon successful login.
 *
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class MainPane extends GenericPane<BorderPane> implements MainPaneCallbacks {
    
    /**
     * The top pane of this BorderPane.
     */
    private TopPane topPane;
    
    /**
     * The initial center pane for this BorderPane.
     */
    private HomePane homePane;
    
    /**
     * Constructs a new MainPane pane that extends BorderPane and displays the main user
     * interface that all other panes are placed upon.
     */
    public MainPane(final Callbacks callbacks) {
        super(new BorderPane(), callbacks);
        topPane = new TopPane(this, callbacks);
        pane.setTop(topPane.getPane());
        homePane = new HomePane(this, topPane);
        pane.setCenter(homePane.getPane());
    }
    
    /**
     * Changes the pane in the center of this pane.
     */
    @Override
    public void changeCenterPane(final GenericPane<?> pane) {
        this.pane.setCenter(pane.getPane());
    }
}