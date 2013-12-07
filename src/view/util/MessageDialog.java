
package view.util;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Dialog that pops up with a message
 * @author Srdjan
 *
 */
public class MessageDialog extends Stage {
    
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;
    private static final int EXPIRATION = 3000;
    
    private BorderPane root;
    private Scene scene;
    
    public MessageDialog(final Stage owner) {
        
        root = new BorderPane();
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
    }
    
    public void showDialog(final String message, final boolean status) {
        StatusText text = new StatusText();
        text.setText(message, status);
        
        root.setCenter(text);
        setScene(scene);
        show();
        
    }
}
