
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
import view.util.SceneCallbacks;
import view.util.CustomFileChooser;
import view.util.CustomTable;
import view.util.GenericPane;
import view.util.CenterPaneCallbacks;
import view.util.MessageDialog;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
import controller.user.LoggedUser;

/**
 * JavaFX pane responsible for displaying information about a selected paper.
 * 
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class PaperPane extends GenericPane<GridPane> implements EventHandler, AddUserCallback {
    
    private static final int DOUBLE_CLICK = 2;
    private Text paperNameText;
    private Text paperDescriptionText;
    private Button assignReviewer;
    private Button submitReviewButton;
    private Button recommendPaperButton;
    private Button reuploadPaperButton;
    private Button removePaperButton;
    private Button downloadPaperButton;
    private Button acceptRejectPaperButton;
    
    private CustomTable<ReviewRow> paperReviewsTable;
    private CustomTable<ConferenceUserRow> reviewersTable;
    
    private String[] paperTableColumnNames = { "Review" };
    private String[] paperTableVariableNames = { "id" };
    
    private final String[] reviewersTableColumnNames = { "Name", "Role" };
    private final String[] reviewersTableVariableNames = { "name", "role" };
    
    private Paper paper;
    private List<Review> listOfReviews;
    private List<ConferenceUser> listOfReviewers;
    private int paperID;
    private boolean isReviewed;
    private CustomFileChooser fileChooser;
    
    public PaperPane(final int paperID, final SceneCallbacks callbacks,
            final CenterPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane(), callbacks);
        this.paperID = paperID;
        addCenterPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBack(progressSpinnerCallbacks);
        
        fileChooser = new CustomFileChooser();
        
        paperReviewsTable = new CustomTable<ReviewRow>(paperTableColumnNames,
                paperTableVariableNames);
        reviewersTable = new CustomTable<ConferenceUserRow>(reviewersTableColumnNames,
                reviewersTableVariableNames);
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));
        
        loadPaper();
    }
    
    @Override
    public GenericPane<GridPane> refresh() {
        return new PaperPane(paperID, sceneCallback, centerPaneCallback, progressSpinnerCallback);
    }
    
    private void loadPaper() {
        new LoadPaperService(progressSpinnerCallback).start();
        new LoadDataService(progressSpinnerCallback).start();
    }
    
    //TODO need to add permission checks for buttons
    /**
     * Creates the main components of the ConferencePane pane.
     */
    private void create() {
        
        paperNameText = new Text("Paper: " + paper.getTitle());
        paperNameText.setId("conf-text");
        
        paperDescriptionText = new Text("Description: " + paper.getDescription());
        paperDescriptionText.setId("conf-text");
        
        pane.add(paperNameText, 0, 0);
        pane.add(paperDescriptionText, 0, 1);
        
        Text reviewTablesText = new Text("Reviews");
        reviewTablesText.setId("header2");
        pane.add(reviewTablesText, 0, 3);
        
        paperReviewsTable.setOnMouseClicked(this);
        pane.add(paperReviewsTable, 0, 4);
        
        Text reviewersTablesText = new Text("Reviewers");
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
        
        HBox bottomBox = new HBox(12);
        
        bottomBox.getChildren().add(removePaperButton);
        
        bottomBox.getChildren().add(recommendPaperButton);
        bottomBox.getChildren().add(reuploadPaperButton);
        bottomBox.getChildren().add(downloadPaperButton);
        bottomBox.getChildren().add(acceptRejectPaperButton);
        
        bottomBox.getChildren().add(submitReviewButton);
        
        bottomBox.getChildren().add(assignReviewer);
        pane.add(bottomBox, 0, 7);
    }
    
    /**
     * Populates the tables with data from the database.
     */
    private void populate() {
        if (listOfReviews != null) {
            for (Review r : listOfReviews) {
                paperReviewsTable.add(new ReviewRow(r.getID(), "Review #" + r.getID()));
            }
            paperReviewsTable.updateItems();
        }
        
        if (listOfReviewers != null) {
            for (ConferenceUser u : listOfReviewers) {
                reviewersTable.add(new ConferenceUserRow(u.getUserID(), u.getUsername(), u
                        .getRole()));
            }
            reviewersTable.updateItems();
        }
    }
    
    /**
     * Event handler for handling table and button click events.
     */
    @Override
    public void handle(final Event event) {
        Object source = event.getSource();
        if (source == paperReviewsTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int paperID = paperReviewsTable.getSelectionModel().getSelectedItem().getId();
            }
        }
        else if (source == reviewersTable) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                int userID = reviewersTable.getSelectionModel().getSelectedItem().getID();
                centerPaneCallback.pushPane(new UserPane(userID, sceneCallback, centerPaneCallback,
                        progressSpinnerCallback));
            }
        }
        else if (source == assignReviewer) {
            new AddReviewersPane(paper.getConferenceID(), sceneCallback.getPrimaryStage(),
                    progressSpinnerCallback, this).showDialog();
            // TODO new UsersPane(callbacks.getPrimaryStage(), progressSpinnerCallbacks, paper.getConferenceID(), PermissionLevel.REVIEWER).showDialog();
        }
        else if (source == submitReviewButton) {
            File file = fileChooser.showOpenDialog(sceneCallback.getPrimaryStage());
            if (file != null) {
                new SubmitReviewService(progressSpinnerCallback, file).start();
            }
        }
        else if (source == recommendPaperButton) {
            new RecommendPaperService(progressSpinnerCallback).start();
        }
        else if (source == reuploadPaperButton) {
            File file = fileChooser.showOpenDialog(sceneCallback.getPrimaryStage());
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
            new PaperStatePane(sceneCallback.getPrimaryStage(), progressSpinnerCallback, paperID)
                    .showDialog();
        }
    }
    
    @Override
    public void addReviewer(final int userID) {
        new AddReviewerService(progressSpinnerCallback, userID).start();
    }
    
    private class AddReviewerService extends ProgressSpinnerService {
        
        private int userID;
        
        public AddReviewerService(final ProgressSpinnerCallbacks progressSpinnerCallbacks,
                final int userID) {
            super(progressSpinnerCallbacks);
            this.userID = userID;
        }
        
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                
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
        
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                refresh();
            }
            super.succeeded();
        }
    }
    
    private class ReUploadPaperService extends ProgressSpinnerService {
        
        private File file;
        
        public ReUploadPaperService(final ProgressSpinnerCallbacks progressSpinnerCallbacks,
                final File file) {
            super(progressSpinnerCallbacks);
            this.file = file;
        }
        
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                
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
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(
                                e.getMessage(), false);
                        
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
                        PaperManager.removePaper(paperID, LoggedUser.getInstance().getUser()
                                .getID());
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        //TODO make sure message dialog works
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(
                                e.getMessage(), false);
                        
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
                                .getUser().getID());
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        //TODO make sure message dialog works
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(
                                e.getMessage(), false);
                        
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
                        listOfReviews = ReviewManager.getReviews(paperID);
                        listOfReviewers = PaperManager.getAssignedUsers(paperID);
                        
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(
                                e.getMessage(), false);
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
        
        public SubmitReviewService(final ProgressSpinnerCallbacks progressSpinnerCallbacks,
                final File file) {
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
                        ReviewManager.submitReview(paperID, LoggedUser.getInstance().getUser()
                                .getID(), file);
                        
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        new MessageDialog(sceneCallback.getPrimaryStage()).showDialog(
                                e.getMessage(), false);
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