package view.users;

import view.util.CustomProgressIndicator;
import javafx.concurrent.Service;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX pane responsible for displaying the users registered in the database.
 * 
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class UsersPane extends Stage {
    /**
     * The default width.
     */
    private static final int DEFAULT_WIDTH = 400;
    
    /**
     * The default height.
     */
    private static final int DEFAULT_HEIGHT = 400;
    
    
    /**
     * The root pane.
     */
    private BorderPane root;

    private Scene scene;
    
    
    public UsersPane(final Stage owner) {
        root = new BorderPane();
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
    }
    
    public void showDialog() {
        root.setCenter(new Button("Example"));
        setScene(scene);
        show();
    }
}
