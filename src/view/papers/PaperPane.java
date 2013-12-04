
package view.papers;

import java.io.File;
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
import model.conferences.ConferenceUser;
import model.papers.Paper;
import model.papers.PaperManager;
import model.permissions.PermissionLevel;
import model.reviews.Review;
import model.reviews.ReviewManager;
import view.conferences.AddUserCallback;
import view.conferences.ConferenceUserRow;
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
 * JavaFX pane responsible for displaying information about a selected paper. TODO there are too
 * many inner classes this is horrible design and can be fixed at some point.
 * 
 * @author Mohammad Juma
 * @author Tim Mikeladze
 * @version 11-23-2013
 */
public class PaperPane extends GenericPane<GridPane> implements EventHandler, AddUserCallback {
    
    /**
     * Number of clicks for a double click.
     */
    private static final int DOUBLE_CLICK = 2;
    
    /**
     * The list of columns in the reviews table.
     */
    private static final String[] reviewsTableColumnNames = { "Review" };
    
    /**
     * The list of variables in the reviews table.
     */
    private static final String[] reviewsTableVariableNames = { "reviewName" };
    
    /**
     * The list of columns in the reviewers table.
     */
    private static final String[] reviewersTableColumnNames = { "Name", "Role" };
    
    /**
     * The list of variables in the reviewers table.
     */
    private static final String[] reviewersTableVariableNames = { "name", "role" };
    
    /**
     * Button for assigning a review.
     */
    private Button assignReviewer;
    
    /**
     * Button for submitting a review
     */
    private Button submitReviewButton;
    
    /**
     * Button for recommending a paper.
     */
    private Button recommendPaperButton;
    
    /**
     * Button for re-uploading a paper.
     */
    private Button reuploadPaperButton;
    
    /**
     * Button for removing a paper.
     */
    private Button removePaperButton;
    
    /**
     * Button for downloading a paper.
     */
    private Button downloadPaperButton;
    
    /**
     * Button for accepting or rejecting a paper.
     */
    private Button acceptRejectPaperButton;
    
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
    public PaperPane(final int paperID, final SceneCallbacks sceneCallback, final CenterPaneCallbacks centerPaneCallback,
            final ProgressSpinnerCallbacks progressSpinnerCallback) {
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
        return new PaperPane(paperID, sceneCallback, centerPaneCallback, progressSpinnerCallback);
    }
    
    /**
     * Loads the papers information from the database.
     */
    private void loadPaper() {
        new LoadPaperService(progressSpinnerCallback).start();
        new LoadDataService(progressSpinnerCallback).start();
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
        
        final Text reviewTablesText = new Text("Reviews");
        reviewTablesText.setId("header2");
        pane.add(reviewTablesText, 0, 3);
        
        paperReviewsTable.setOnMouseClicked(this);
        pane.add(paperReviewsTable, 0, 4);
        
        final Text reviewersTablesText = new Text("Reviewers");
        reviewersTablesText.setId("header2");
        pane.add(reviewersTablesText, 0, 5);
        
        reviewersTable.setOnMouseClicked(this);
        pane.add(reviewersTable, 0, 6);
        
        removePaperButton = new Button("Remove Paper");
        removePaperButton.setOnAction(this);
        
        recommendPaperButton = new Button("Recommend Paper");
        recommendPaperButton.setOnAction(this);
        
        reuploadPaperButton = new Button("Reupload Paper");
        reuploadPaperButton.setOnAction(this);
        
        submitReviewButton = new Button("Submit Review");
        submitReviewButton.setOnAction(this);
        
        assignReviewer = new Button("Add Reviewer");
        assignReviewer.setOnAction(this);
        
        downloadPaperButton = new Button("Download Paper");
        downloadPaperButton.setOnAction(this);
        
        acceptRejectPaperButton = new Button("Accept / Reject Paper");
        acceptRejectPaperButton.setOnAction(this);
        
        final HBox bottomBox = new HBox(12);
        
        bottomBox.getChildren()
                 .add(removePaperButton);
        bottomBox.getChildren()
                 .add(recommendPaperButton);
        bottomBox.getChildren()
                 .add(reuploadPaperButton);
        bottomBox.getChildren()
                 .add(downloadPaperButton);
        bottomBox.getChildren()
                 .add(acceptRejectPaperButton);
        bottomBox.getChildren()
                 .add(submitReviewButton);
        bottomBox.getChildren()
                 .add(assignReviewer);
        
        pane.add(bottomBox, 0, 7);
    }
    
    /**
     * Populates the tables with data from the database.
     */
    private void populate() {
        if (listOfReviews != null) {
            int i = 1;
            for (Review review : listOfReviews) {
                paperReviewsTable.add(new ReviewRow(review.getID(), "Review #" + i));
                i++;
            }
            paperReviewsTable.updateItems();
        }
        if (listOfReviewers != null) {
            for (ConferenceUser user : listOfReviewers) {
                reviewersTable.add(new ConferenceUserRow(user.getUserID(), user.getUsername(), user.getRole()));
            }
            reviewersTable.updateItems();
        }
    }
    
    /**
     * Event handler for handling table and button click events.
     */
    @Override
    public void handle(final Event event) {
        final Object source = event.getSource();
        if (source == paperReviewsTable) {
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int paperID = paperReviewsTable.getSelectionModel()
                                               .getSelectedItem()
                                               .getId();
            }
        }
        else if (source == reviewersTable) {
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int userID = reviewersTable.getSelectionModel()
                                           .getSelectedItem()
                                           .getID();
                centerPaneCallback.pushPane(new UserPane(userID, sceneCallback, centerPaneCallback, progressSpinnerCallback));
            }
        }
        else if (source == assignReviewer) {
            new AddReviewersPane(paper.getConferenceID(), sceneCallback.getPrimaryStage(), progressSpinnerCallback, this).showDialog();
            // TODO new UsersPane(callbacks.getPrimaryStage(), progressSpinnerCallbacks, paper.getConferenceID(), PermissionLevel.REVIEWER).showDialog();
        }
        else if (source == submitReviewButton) {
            final File file = fileChooser.showOpenDialog(sceneCallback.getPrimaryStage());
            if (file != null) {
                new SubmitReviewService(progressSpinnerCallback, file).start();
            }
        }
        else if (source == recommendPaperButton) {
            new RecommendPaperService(progressSpinnerCallback).start();
        }
        else if (source == reuploadPaperButton) {
            final File file = fileChooser.showOpenDialog(sceneCallback.getPrimaryStage());
            if (file != null) {
                new ReUploadPaperService(progressSpinnerCallback, file).start();
            }
        }
        else if (source == removePaperButton) {
            new RemovePaperService(progressSpinnerCallback).start();
        }
        else if (source == downloadPaperButton) {
            File saveLocation = fileChooser.showSaveDialog(sceneCallback.getPrimaryStage());
        }
        else if (source == acceptRejectPaperButton) {
            new PaperStatePane(sceneCallback.getPrimaryStage(), progressSpinnerCallback, paperID).showDialog();
        }
    }
    
    /**
     * TODO hmm
     */
    @Override
    public void addReviewer(final int userID) {
        new AddReviewerService(progressSpinnerCallback, userID).start();
    }
    
    /**
     * Starts a service that connects to the database and queries for papers and reviews.
     * 
     * @author Tim Mikeladze
     * @version 11-24-2013
     */
    private class AddReviewerService extends ProgressSpinnerService {
        
        /**
         * The users id.
         */
        private int userID;
        
        /**
         * Starts the service.
         * 
         * @param progressSpinnerCallback The progress spinner.
         * @param userID The users id.
         */
        public AddReviewerService(final ProgressSpinnerCallbacks progressSpinnerCallback, final int userID) {
            super(progressSpinnerCallback);
            this.userID = userID;
        }
        
        /**
         * Creates a new task for loading table lists.
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
                        PaperManager.assignPaper(paperID, userID, PermissionLevel.REVIEWER);
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when data loading is done to populate tables.
         */
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                refresh();
            }
            super.succeeded();
        }
    }
    
    /**
     * Starts a service that lets the author re-upload this paper.
     * 
     * @author Tim Mikeladze
     * @version 11-24-2013
     */
    private class ReUploadPaperService extends ProgressSpinnerService {
        
        /**
         * The file to re-upload.
         */
        private File file;
        
        /**
         * Creates the new service.
         * 
         * @param progressSpinnerCallback The progress spinner
         * @param file The file to upload
         */
        public ReUploadPaperService(final ProgressSpinnerCallbacks progressSpinnerCallback, final File file) {
            super(progressSpinnerCallback);
            this.file = file;
        }
        
        /**
         * Creates a new task for loading table lists.
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
                        PaperManager.reuploadPaper(paperID, file);
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when uploading is done.
         */
        @Override
        protected void succeeded() {
            super.succeeded();
        }
    }
    
    private class RecommendPaperService extends ProgressSpinnerService {
        
        public RecommendPaperService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
            super(progressSpinnerCallbacks);
        }
        
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                
                @Override
                protected String call() {
                    try {
                        PaperManager.recommendPaper(paperID);
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
            super.succeeded();
        }
    }
    
    private class RemovePaperService extends ProgressSpinnerService {
        
        public RemovePaperService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
            super(progressSpinnerCallbacks);
        }
        
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                
                @Override
                protected String call() {
                    try {
                        PaperManager.removePaper(paperID, LoggedUser.getInstance()
                                                                    .getUser()
                                                                    .getID());
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
                centerPaneCallback.popPane();
            }
            super.succeeded();
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
                        isReviewed = ReviewManager.isReviewed(paperID, LoggedUser.getInstance()
                                                                                 .getUser()
                                                                                 .getID());
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
    
    private class LoadDataService extends ProgressSpinnerService {
        
        public LoadDataService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
            super(progressSpinnerCallbacks);
        }
        
        /**
         * Creates a new task for loading table lists.
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
                        System.out.println("a");
                        listOfReviews = ReviewManager.getReviews(paperID);
                        System.out.println("b");
                        listOfReviewers = PaperManager.getAssignedUsers(paperID);
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
                populate();
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
                        ReviewManager.submitReview(paperID, LoggedUser.getInstance()
                                                                      .getUser()
                                                                      .getID(), file);
                        
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