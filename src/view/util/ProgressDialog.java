
package view.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is a progress dialog, it shows a progress dialog which is updated via a service
 * 
 * @author Tim Mikeladze
 * @version 11-17-2013
 */
public class ProgressDialog extends Stage {
    
    /**
     * The default width.
     */
    private static final int DEFAULT_WIDTH = 100;
    
    /**
     * The default height.
     */
    private static final int DEFAULT_HEIGHT = 100;
    
    /**
     * The service.
     */
    private Service<?> service;
    
    /**
     * The root pane.
     */
    private BorderPane root;
    
    /**
     * The progress indicator.
     */
    private CustomProgressIndicator indicator;
    
    /**
     * The status of the progress dialog.
     */
    private boolean cancellable;
    
    /**
     * The progress dialogs scene.
     */
    private Scene scene;
    
    /**
     * Creates a new Progress Dialog.
     * 
     * @param service the service
     * @param owner the owner
     * @param width the height
     * @param height the width
     */
    public ProgressDialog(final Service<?> service, final Stage owner, final int width, final int height) {
        this.service = service;
        
        root = new BorderPane();
        indicator = new CustomProgressIndicator();
        scene = new Scene(root, width, height);
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
    }
    
    /**
     * Wrapper for ProgresDialog.
     * 
     * @param service the service
     * @param owner the owner
     */
    public ProgressDialog(final Service<?> service, final Stage owner) {
        this(service, owner, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    /**
     * Sets the whether the dialog and service and be cancelled via a button.
     * 
     * @param cancellable is cancellable
     */
    public void setCancellable(final boolean cancellable) {
        this.cancellable = cancellable;
    }
    
    /**
     * Creates and shows the dialog
     */
    public void showDialog() {
        indicator.progressProperty()
                 .bind(service.progressProperty());
        service.stateProperty()
               .addListener(new ChangeListener<State>() {
                   
                   @Override
                   public void changed(final ObservableValue<? extends State> observable, final State oldValue, final State newValue) {
                       if (newValue == State.CANCELLED || newValue == State.FAILED || newValue == State.SUCCEEDED) {
                           ProgressDialog.this.hide();
                       }
                   }
               });
        
        if (cancellable) {
            Button button = new Button("Cancel");
            button.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(final ActionEvent event) {
                    service.cancel();
                }
            });
            root.setBottom(button);
        }
        
        root.setCenter(indicator);
        setScene(scene);
        show();
    }
    
}
