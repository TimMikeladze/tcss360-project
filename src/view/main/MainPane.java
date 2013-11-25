
package view.main;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
    
    private TopPane topPane;
    private SidePane sidePane;
    private HomePane homePane;
    
    /**
     * Constructs a new MainPane pane that extends BorderPane and displays the main user
     * interface that all other panes are placed upon.
     */
    public MainPane(final Callbacks callbacks) {
        super(new BorderPane(), callbacks);
        topPane = new TopPane(callbacks);
        pane.setTop(topPane.getPane());
        //sidePane = new SidePane(callbacks);
        //pane.setLeft(sidePane.getPane());
        homePane = new HomePane(this, topPane);
        pane.setCenter(homePane.getPane());
    }

    @Override
    public void changeCenterPane(GenericPane<?> pane) {
        this.pane.setCenter(pane.getPane());        
    }
    
//    /**
//     * Creates a pane for displaying the main interface of the program.
//     * 
//     * @return stackPane The center pane of MainPane's BorderPane.
//     */
//    private StackPane centerPane() {
//        final StackPane stackPane = new StackPane();
//        
//        // stackPane.getStylesheets().add("style.css");
//        //stackPane.getChildren().add(new ConferencePane(Conference.conferenceFromID(151)).getPane());
//        
//        stackPane.getChildren()
//                 .add(new HomePane(topPane).getPane());
//        
//        return stackPane;
//    }
}