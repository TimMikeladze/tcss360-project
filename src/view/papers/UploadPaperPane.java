
package view.papers;

import java.io.File;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.papers.PaperManager;
import view.util.CenterPaneCallbacks;
import view.util.CustomFileChooser;
import view.util.GenericPane;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
import view.util.SceneCallbacks;
import view.util.StatusText;
import view.util.Validator;
import controller.user.LoggedUser;
/**
 * Pane for a user to upload a paper into a conference.
 * @author Srdjan S.
 * @version 11-20-13
 */
public class UploadPaperPane extends GenericPane<GridPane> implements EventHandler<ActionEvent> {
    
    private Text scenetitle;
    private Label paperNameLabel;
    private TextField paperNameTextField;
    private Label paperDescriptionLabel;
    private TextField paperDescriptionTextField;
    private Button uploadPaperButton;
    private StatusText statusText;
    private CustomFileChooser fileChooser;
    private int conferenceID;
    
    /**
     * Constructs a paper to upload papers
     * @param conferenceID 
     * @param callbacks
     * @param mainPaneCallbacks
     * @param progressSpinnerCallbacks
     */
    public UploadPaperPane(final int conferenceID, final SceneCallbacks callbacks, final CenterPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane());
        this.conferenceID = conferenceID;
        addSceneCallback(callbacks);
        addCenterPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBack(progressSpinnerCallbacks);
        
        fileChooser = new CustomFileChooser();
        
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        
        create();
    }
    
    /**
     * Refreshes the pane
     */
    @Override
    public GenericPane<GridPane> refresh() {
        return new UploadPaperPane(conferenceID, sceneCallback, centerPaneCallback, progressSpinnerCallback);
    }
    
    /**
     * Creates the pane with the necessary content
     */
    private void create() {
        scenetitle = new Text("Upload paper");
        scenetitle.setId("header1");
        pane.add(scenetitle, 0, 0);
        
        paperNameLabel = new Label("Title:");
        pane.add(paperNameLabel, 0, 1);
        
        paperNameTextField = new TextField();
        pane.add(paperNameTextField, 1, 1);
        
        paperDescriptionLabel = new Label("Description:");
        pane.add(paperDescriptionLabel, 0, 2);
        
        paperDescriptionTextField = new TextField();
        pane.add(paperDescriptionTextField, 1, 2);
        
        uploadPaperButton = new Button("Upload");
        
        HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        
        buttonHBox.getChildren()
                  .add(uploadPaperButton);
        
        pane.add(buttonHBox, 1, 5);
        
        statusText = new StatusText();
        pane.add(statusText, 1, 6);
        
        uploadPaperButton.setOnAction(this);
    }
    
    /**
     * Event handler.
     */
    @Override
    public void handle(final ActionEvent event) {
        if (event.getSource() == uploadPaperButton) {
            uploadPaper();
        }
    }
    
    /**
     * Uploads the paper provided by user
     */
    private void uploadPaper() {
        String name = paperNameTextField.getText();
        String description = paperDescriptionTextField.getText();
        
        File file = fileChooser.showOpenDialog(sceneCallback.getPrimaryStage());
        
        if (!Validator.isEmpty(name, description) && file != null) {
            new CreatePaperService(progressSpinnerCallback, name, description, file).start();
        }
        else if (file == null) {
            statusText.setErrorText("Missing file");
        }
        else {
            statusText.setErrorText("Missing field");
        }
    }
    
    /**
     * Service for creating the paper.
     */
    private class CreatePaperService extends ProgressSpinnerService {
        
        private String name;
        private String description;
        private File file;
        
        public CreatePaperService(final ProgressSpinnerCallbacks progressSpinnerCallbacks, final String name, final String description,
                final File file) {
            super(progressSpinnerCallbacks);
            this.name = name;
            this.description = description;
            this.file = file;
        }
        
        /**
         * Creates a new task
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
                        int userID = LoggedUser.getInstance()
                                               .getUser()
                                               .getID();
                        
                        PaperManager.submitPaper(conferenceID, userID, name, description, file);
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        statusText.setErrorText(e.getMessage());
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when a paper loading is done to populate table
         */
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                centerPaneCallback.popPane();
            }
            super.succeeded();
        }
    }
}
