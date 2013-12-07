
package view.papers;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.papers.PaperManager;
import model.papers.PaperStatus;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;

/**
 * Pane for changing the state of the paper to accepted or rejected.
 * 
 * @author Srdjan
 * @version 11-29-2013
 */
public class PaperStatePane extends Stage implements EventHandler {
    
    /**
     * Number of clicks for a double click.
     */
    private static final int DOUBLE_CLICK = 2;
    
    /**
     * The default width.
     */
    private static final int DEFAULT_WIDTH = 280;
    
    /**
     * The default height.
     */
    private static final int DEFAULT_HEIGHT = 90;
    
    /**
     * The papers id.
     */
    private final int paperId;
    
    /**
     * The root pane.
     */
    private GridPane root;
    
    /**
     * The scene.
     */
    private Scene scene;
    
    /**
     * The progress spinner.
     */
    private ProgressSpinnerCallbacks progressSpinnerCallback;
    
    /**
     * The approve button.
     */
    private Button approveButton;
    
    /**
     * The reject button.
     */
    private Button rejectButton;
    
    /**
     * Creates a new window for choosing the state of the paper.
     * 
     * @param owner The window calling this class.
     * @param progressSpinnerCallback The progress spinner.
     * @param paperId The papers id.
     */
    public PaperStatePane(final Stage owner, final ProgressSpinnerCallbacks progressSpinnerCallback, final int paperId) {
        this.progressSpinnerCallback = progressSpinnerCallback;
        this.paperId = paperId;
        root = new GridPane();
        root.setStyle("padding: 10px;");
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
    }
    
    /**
     * Displays the dialog for the user to make a choice.
     */
    public void showDialog() {
        final Text approveRejectText = new Text("Would you like to approve or reject?");
        approveRejectText.setStyle("-fx-fill:black; -fx-font-size:13px; -fx-font-weight:bold;");
        approveRejectText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        approveButton = new Button("Approve");
        approveButton.setStyle("-fx-text-fill: bisque; -fx-background-color: linear-gradient(crimson, darkred);");
        approveButton.setOnMouseClicked(this);
        rejectButton = new Button("Reject");
        rejectButton.setStyle("-fx-text-fill: bisque; -fx-background-color: linear-gradient(crimson, darkred);");
        rejectButton.setOnMouseClicked(this);
        
        final HBox buttonsBox = new HBox(4);
        buttonsBox.getChildren().add(approveButton);
        buttonsBox.getChildren().add(rejectButton);
        
        root.add(approveRejectText, 0, 0);
        root.add(buttonsBox, 0, 1);
        
        setScene(scene);
        show();
    }
    
    /**
     * Event handler for handling table and button click events.
     */
    @Override
    public void handle(final Event event) {
        final Object source = event.getSource();
        if (source == approveButton) {
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                close();
                new ChangePaperStateService(progressSpinnerCallback, paperId, PaperStatus.ACCEPTED).start();;
            }
        }
        if (source == rejectButton) {
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                close();
                new ChangePaperStateService(progressSpinnerCallback, paperId, PaperStatus.REJECTED).start();;
            }
        }
    }
    
    /**
     * Creates a service that changes the papers state. 
     * 
     * @author Mohammad Juma
     * @version 11-29-2013
     */
    private class ChangePaperStateService extends ProgressSpinnerService {
        
        /**
         * The papers id.
         */
        private int paperID;
        
        /**
         * The papers status.
         */
        private PaperStatus paperStatus;
        
        /**
         * Creates the service.
         * 
         * @param progressSpinnerCallback The progress spinner
         * @param paperID The papers id
         * @param paperStatus The papers status
         */
        public ChangePaperStateService(final ProgressSpinnerCallbacks progressSpinnerCallback, final int paperID,
                final PaperStatus paperStatus) {
            super(progressSpinnerCallback);
            
            this.paperID = paperID;
            this.paperStatus = paperStatus;
        }
        
        /**
         * Creates a new task.
         */
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                
                /**
                 * Calls the new task.
                 */
                @Override
                protected String call() {
                    try {
                        if (paperStatus == PaperStatus.ACCEPTED) {
                            PaperManager.acceptPaper(paperID);
                            System.out.println("Accepted Paper  " + paperID);
                        }
                        else if (paperStatus == PaperStatus.REJECTED) {
                            PaperManager.rejectPaper(paperID);
                            System.out.println("Rejected Paper " + paperID);
                        }
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        //TODO make sure message dialog works
                        //new MessageDialog(callbacks.getPrimaryStage()).showDialog(
                        //        e.getMessage(), false);
                        
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when finished.
         */
        @Override
        protected void succeeded() {
            if (getSuccess()) {
            }
            super.succeeded();
        }
    }
}