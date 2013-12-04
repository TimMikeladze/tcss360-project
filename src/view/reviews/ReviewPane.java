
package view.reviews;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.conferences.ConferenceManager;
import model.conferences.ConferenceUser;
import model.papers.Paper;
import model.papers.PaperManager;
import model.permissions.PermissionLevel;
import model.permissions.Permissions;
import model.reviews.Review;
import model.reviews.ReviewManager;
import view.conferences.AddUserCallback;
import view.conferences.ConferenceUserRow;
import view.papers.AddReviewersPane;
import view.reviews.ReviewRow;
import view.users.UserPane;
import view.util.CenterPaneCallbacks;
import view.util.CustomFileChooser;
import view.util.CustomTable;
import view.util.GenericPane;
import view.util.MessageDialog;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
import view.util.SceneCallbacks;
import controller.user.LoggedUser;

/**
 * JavaFX pane responsible for displaying information about a selected paper.
 * TODO there are too many inner classes this is horrible design and can be fixed at some point.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
 * @version 11-23-2013
 */
public class ReviewPane extends GenericPane<GridPane> implements EventHandler {
    
    /**
     * The list of columns in the reviews table.
     */
    private static final String[] reviewsTableColumnNames = { "Review" };
    
    /**
     * The list of variables in the reviews table.
     */
    private static final String[] reviewsTableVariableNames = { "id" };
    
    /**
     * The list of columns in the reviewers table.
     */
    private static final String[] reviewersTableColumnNames = { "Name", "Role" };
    
    /**
     * The list of variables in the reviewers table.
     */
    private static final String[] reviewersTableVariableNames = { "name", "role" };

    /**
     * Button for downloading a paper.
     */
    private Button downloadPaperButton;
    
    /**
     * Button for submitting a review
     */
    private Button submitReviewButton;
    
    /**
     * Button for removing a paper.
     */
    private Button removeReviewButton;

    /**
     * Button for downloading a review.
     */
    private Button downloadReviewButton;
    
    /**
     * The current paper.
     */
    private Paper paper;
    
    /**
     * A table that displays all the reviews a paper has.
     */
    private CustomTable<ReviewRow> paperReviewsTable;
    
    /**
     * A table that displays all the reviewers.
     */
    private CustomTable<ConferenceUserRow> reviewersTable;
    
    /**
     * The list of reviews for this paper.
     */
    private List<Review> listOfReviews;
    
    /**
     * The list of reviewers for this paper.
     */
    private List<ConferenceUser> listOfReviewers;
    
    /**
     * The current papers id.
     */
    private int paperID;
    
    /**
     * The status of this paper.
     */
    private boolean isReviewed;
    
    /**
     * The file chooser for uploading something? TODO what is this for?
     */
    private CustomFileChooser fileChooser;
    
    /**
     * Constructs a new PaperPane for managing a paper.
     * 
     * @param paperID The papers id.
     * @param sceneCallback A callback to the scene this pane is in
     * @param centerPaneCallback A callback to the center pane
     * @param progressSpinnerCallback A callback to the progress spinner
     */
    public ReviewPane(final int paperID, final SceneCallbacks sceneCallback,
            final CenterPaneCallbacks centerPaneCallback, final ProgressSpinnerCallbacks progressSpinnerCallback) {
        super(new GridPane(), sceneCallback);
        this.paperID = paperID;
        addCenterPaneCallBacks(centerPaneCallback);
        addProgressSpinnerCallBack(progressSpinnerCallback);
        
        fileChooser = new CustomFileChooser();
        paperReviewsTable = new CustomTable<ReviewRow>(reviewsTableColumnNames, reviewsTableVariableNames);
        reviewersTable = new CustomTable<ConferenceUserRow>(reviewersTableColumnNames, reviewersTableVariableNames);
        
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));
        
        loadPaper();
    }
    
    /**
     * Refreshes the current pane after data has been changed.
     */
    @Override
    public GenericPane<GridPane> refresh() {
        return new ReviewPane(paperID, sceneCallback, centerPaneCallback, progressSpinnerCallback);
    }
    
    /**
     * Loads the papers information from the database.
     */
    private void loadPaper() {
        new LoadPaperService(progressSpinnerCallback).start();
    }
    
    //TODO need to add permission checks for buttons
    /**
     * Creates the main components of the ConferencePane pane.
     */
    private void create() {
        
        final Text paperNameText = new Text("Paper: " + paper.getTitle());
        paperNameText.setId("conf-text");
        pane.add(paperNameText, 0, 0);
        
        final Text paperDescriptionText = new Text("Description: " + paper.getDescription());
        paperDescriptionText.setId("conf-text");
        pane.add(paperDescriptionText, 0, 1);
        
        final Text reviewTablesText = new Text("Review Details");
        reviewTablesText.setId("header2");
        pane.add(reviewTablesText, 0, 2);
        
        final Text statusText = new Text("Paper Status: " + paper.getStatus());
        statusText.setId("conf-text");
        pane.add(statusText, 0, 3);
        
        downloadPaperButton = new Button("Download Paper");
        downloadPaperButton.setOnAction(this);
        
        submitReviewButton = new Button("Submit Review");
        submitReviewButton.setOnAction(this);
        
        downloadReviewButton = new Button("Download Review");
        downloadReviewButton.setOnAction(this);
      
        final HBox bottomBox = new HBox(12);
        
        if (Permissions.hasPermission(ReviewManager.class, "getSubmittedReview", LoggedUser.getInstance()
                .getPermissions())) {
        	bottomBox.getChildren().add(downloadPaperButton);
        }
        if (Permissions.hasPermission(ReviewManager.class, "submitReview", LoggedUser.getInstance()
                .getPermissions())) {
        	bottomBox.getChildren().add(submitReviewButton);
        }
        if (Permissions.hasPermission(ReviewManager.class, "getSubmittedReview", LoggedUser.getInstance()
                .getPermissions())) {
        	bottomBox.getChildren().add(downloadReviewButton);
        }
        
        pane.add(bottomBox, 0, 4);
    }
    
    /**
     * Event handler for handling table and button click events.
     */
    @Override
    public void handle(final Event event) {
        final Object source = event.getSource();
        final File saveLocation;
        
        if (source == downloadPaperButton) {
        	saveLocation = fileChooser.showSaveDialog(sceneCallback.getPrimaryStage());
            
            File file = new File(saveLocation.getAbsoluteFile() + "." + paper.getFileExtension());
            
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    FileOutputStream fop = new FileOutputStream(file);
                    
                    // get the content in bytes
                    byte[] contentInBytes = paper.getFile()
                                                 .getBytes();
                    
                    fop.write(contentInBytes);
                    fop.flush();
                    fop.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } 
        else if (source == submitReviewButton) {
            final File file = fileChooser.showOpenDialog(sceneCallback.getPrimaryStage());
            if (file != null) {
                new SubmitReviewService(progressSpinnerCallback, file).start();
            }
        }
        else if (source == downloadReviewButton) {
        	saveLocation = fileChooser.showSaveDialog(sceneCallback.getPrimaryStage());
            
            File file = new File(saveLocation.getAbsoluteFile() + "." + paper.getFileExtension());
            
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    FileOutputStream fop = new FileOutputStream(file);
                    
                    // get the content in bytes
                    byte[] contentInBytes = paper.getFile()
                                                 .getBytes();
                    
                    fop.write(contentInBytes);
                    fop.flush();
                    fop.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private class LoadPaperService extends ProgressSpinnerService {
        
        public LoadPaperService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
            super(progressSpinnerCallbacks);
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
                        paper = Paper.paperFromID(paperID);
                        isReviewed = ReviewManager.isReviewed(paperID, LoggedUser.getInstance().getUser().getID());
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        //TODO make sure message dialog works
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(e.getMessage(), false);
                        
                    }
                    return null;
                }
            };
        }
        
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                create();
            }
            super.succeeded();
        }
    }
    
    
    private class SubmitReviewService extends ProgressSpinnerService {
        
        private File file;
        
        public SubmitReviewService(final ProgressSpinnerCallbacks progressSpinnerCallbacks, final File file) {
            super(progressSpinnerCallbacks);
            this.file = file;
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
                        ReviewManager.submitReview(paperID, LoggedUser.getInstance().getUser().getID(), file);
                        
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(e.getMessage(), false);
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when data loading is done to populate tables
         */
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                refresh();
            }
            super.succeeded();
        }
    }
    
}