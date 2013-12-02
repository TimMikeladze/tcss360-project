
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

public class PaperStatePane extends Stage implements EventHandler {
    
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
     * The root pane.
     */
    private GridPane root;
    
    /**
     * The scene.
     */
    private Scene scene;
    
    private ProgressSpinnerCallbacks progressSpinnerCallbacks;
    
    private Button approveButton;
    
    private Button rejectButton;
    
    private int paperId;
    
    public PaperStatePane(final Stage owner,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks, final int paperId) {
        this.progressSpinnerCallbacks = progressSpinnerCallbacks;
        this.paperId = paperId;
        root = new GridPane();
        root.setStyle("padding: 10px;");
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
    }
    
    public void showDialog() {
        Text approveRejectText = new Text("Would you like to approve or reject?");
        approveRejectText.setStyle("-fx-fill:black; -fx-font-size:13px; -fx-font-weight:bold;");
        approveRejectText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        approveButton = new Button("Approve");
        approveButton
                .setStyle("-fx-text-fill: bisque; -fx-background-color: linear-gradient(crimson, darkred);");
        approveButton.setOnMouseClicked(this);
        rejectButton = new Button("Reject");
        rejectButton
                .setStyle("-fx-text-fill: bisque; -fx-background-color: linear-gradient(crimson, darkred);");
        rejectButton.setOnMouseClicked(this);
        
        HBox buttonsBox = new HBox(4);
        buttonsBox.getChildren().add(approveButton);
        buttonsBox.getChildren().add(rejectButton);
        
        root.add(approveRejectText, 0, 0);
        root.add(buttonsBox, 0, 1);
        
        setScene(scene);
        show();
        
    }
    
    @Override
    public void handle(final Event event) {
        Object source = event.getSource();
        if (source == approveButton) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                close();
                new ChangePaperStateService(progressSpinnerCallbacks, paperId,
                        PaperStatus.ACCEPTED);
            }
        }
        if (source == rejectButton) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                close();
                new ChangePaperStateService(progressSpinnerCallbacks, paperId,
                        PaperStatus.REJECTED);
            }
        }
    }
    
    /**
     * Changes the papers state. 
     */
    private class ChangePaperStateService extends ProgressSpinnerService {
        
        private int paperID;
        private PaperStatus paperStatus;
        
        public ChangePaperStateService(final ProgressSpinnerCallbacks progressSpinnerCallbacks,
                final int paperID, final PaperStatus paperStatus) {
            super(progressSpinnerCallbacks);
            
            this.paperID = paperID;
            this.paperStatus = paperStatus;
        }
        
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
        
        @Override
        protected void succeeded() {
            if (getSuccess()) {
            }
            super.succeeded();
        }
    }
    
}

/*


*/