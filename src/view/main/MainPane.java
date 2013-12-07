
package view.main;

import java.util.Stack;

import javafx.scene.layout.BorderPane;
import view.home.HomePane;
import view.util.CenterPaneCallbacks;
import view.util.GenericPane;
import view.util.SceneCallbacks;

/**
 * Main JavaFX pane for holding the applications UI upon successful login.
 * 
 * @author Cathryn
 * @version 11-11-2013
 */
public class MainPane extends GenericPane<BorderPane> implements CenterPaneCallbacks {
    
    /**
     * The top pane of this BorderPane.
     */
    private TopPane topPane;
    
    /**
     * The initial center pane for this BorderPane.
     */
    private HomePane homePane;
    
    /**
     * The stack for handling panes in the center of this Border Pane layout.
     */
    private Stack<GenericPane<?>> stack;
    
    /**
     * Constructs a new MainPane pane that extends BorderPane and displays the main user
     * interface that all other panes are placed upon.
     */
    public MainPane(final SceneCallbacks callbacks) {
        super(new BorderPane(), callbacks);
        stack = new Stack<GenericPane<?>>();
        
        topPane = new TopPane(this, callbacks);
        pane.setTop(topPane.getPane());
        homePane = new HomePane(callbacks, this, topPane);
        pushPane(homePane);
    }
    
    /**
     * Refreshes the current pane after data has been changed.
     */
    @Override
    public GenericPane<BorderPane> refresh() {
        return new MainPane(sceneCallback);
    }
    
    /**
     * Pushes a pane onto the center stack.
     */
    @Override
    public void pushPane(final GenericPane<?> pane) {
        stack.push(pane);
        this.pane.setCenter(pane.getPane());
        updateBackButton();
    }
    
    /**
     * Pops a pane of the center stack.
     */
    @Override
    public GenericPane<?> popPane() {
        GenericPane<?> pane = null;
        if (stack.peek().getClass() != HomePane.class) {
            pane = stack.pop();
        }
        else {
            pane = stack.peek();
        }
        updateBackButton();
        this.pane.setCenter(stack.peek().refresh().getPane());
        return pane;
    }
    
    /**
     * Clears all panes from the stack and returns HomePane to the top of the stack.
     */
    @Override
    public GenericPane<?> clearPanes() {
        stack.clear();
        
        homePane = new HomePane(sceneCallback, this, topPane);
        pushPane(homePane);
        
        return homePane;
    }
    
    /**
     * Updates the state of the back button in the top pane.
     */
    public void updateBackButton() {
        topPane.enableBackButton(stack.size() > 1);
    }
}