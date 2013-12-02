//package view.papers;
//
//import java.util.List;
//
//import javafx.concurrent.Task;
//import javafx.event.Event;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.text.Text;
//import model.conferences.Conference;
//import model.conferences.ConferenceManager;
//import model.conferences.ConferenceUser;
//import model.papers.Paper;
//import model.papers.PaperManager;
//import model.permissions.PermissionLevel;
//import model.reviews.Review;
//import view.conferences.AddUserCallback;
//import view.conferences.ConferenceUserRow;
//import view.conferences.ConferencePane.AddUserService;
//import view.conferences.ConferencePane.LoadDataService;
//import view.reviews.ReviewRow;
//import view.users.UsersPane;
//import view.util.Callbacks;
//import view.util.CustomTable;
//import view.util.GenericPane;
//import view.util.MainPaneCallbacks;
//import view.util.MessageDialog;
//import view.util.ProgressSpinnerCallbacks;
//import view.util.ProgressSpinnerService;
//
///**
// * JavaFX pane responsible for displaying information about a selected paper.
// * 
// * @author Mohammad Juma
// * @version 11-23-2013
// */
//public class PaperPane extends GenericPane<GridPane> implements EventHandler {
//    
//    private static final int DOUBLE_CLICK = 2;
//    private Text paperNameText;
//    private Text paperDescriptionText;
//    private Button submitReviewButton;
//    private Button recommendPaper;
//    private Button reuploadPaper;
//    private Button removePaper;
//    private CustomTable<ReviewRow> paperReviewsTable;
//    private String[] paperTableColumnNames;
//    private String[] paperTableVariableNames;
//    private Paper paper;
//    private List<Review> reviews;
//    
//    /**
//     * Constructs a new Conference Pane that extends GridPane and displays the information about
//     * the given conference.
//     */
//    public PaperPane(final int paperID, final Callbacks callbacks, final MainPaneCallbacks mainPaneCallbacks,
//            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
//        super(new GridPane(), callbacks);
//        addMainPaneCallBacks(mainPaneCallbacks);
//        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
//        
//        paperReviewsTable = new CustomTable<ReviewRow>(paperTableColumnNames, paperTableVariableNames);
//        
//        pane.setAlignment(Pos.TOP_LEFT);
//        pane.setHgap(10);
//        pane.setVgap(10);
//        pane.setPadding(new Insets(0, 5, 5, 5));
//        
//        create();
//    }
//    
//    //TODO need to add permission checks for buttons
//    /**
//     * Creates the main components of the ConferencePane pane.
//     */
//    private void create() {
//        new LoadPaper(progressSpinnerCallbacks, paperID).start();
//        
//        paperNameText = new Text("Paper: " + paper.getTitle());
//        paperNameText.setId("conf-text");
//        
//        paperDescriptionText = new Text("Description: " + paper.getDescription());
//        paperDescriptionText.setId("conf-text");
//        
//        pane.add(paperNameText, 0, 0);
//        pane.add(paperDescriptionText, 1, 0);
//        
//        Text reviewTablesText = new Text("Reviews");
//        reviewTablesText.setId("conf-title");
//        pane.add(reviewTablesText, 0, 3);
//        
//        paperReviewsTable.setOnMouseClicked(this);
//        pane.add(paperReviewsTable, 0, 4);
//        
//        Text conferenceUsersText = new Text("Conference Users");
//        conferenceUsersText.setId("conf-title");
//        
//        pane.add(conferenceUsersText, 0, 5);
//        conferenceUsersTable.setOnMouseClicked(this);
//        pane.add(conferenceUsersTable, 0, 6);
//        
//        removeConferenceButton = new Button("Remove Conference");
//        removeConferenceButton.setOnAction(this);
//        
//        addSubprogramChairButton = new Button("Add Subprogram Chair");
//        addSubprogramChairButton.setOnAction(this);
//        
//        addReviewerButton = new Button("Add Reviewer");
//        addReviewerButton.setOnAction(this);
//        
//        assignPaperButton = new Button("Assign Paper");
//        assignPaperButton.setOnAction(this);
//        
//        uploadPaperButton = new Button("Upload Paper");
//        uploadPaperButton.setOnAction(this);
//        
//        uploadReviewButton = new Button("Upload Review");
//        uploadReviewButton.setOnAction(this);
//        
//        HBox bottomBox = new HBox(12);
//        bottomBox.getChildren()
//                 .add(removeConferenceButton);
//        bottomBox.getChildren()
//                 .add(addSubprogramChairButton);
//        bottomBox.getChildren()
//                 .add(addReviewerButton);
//        bottomBox.getChildren()
//                 .add(uploadPaperButton);
//        bottomBox.getChildren()
//                 .add(uploadReviewButton);
//        
//        pane.add(bottomBox, 0, 7);
//    }
//    
//    /**
//     * Populates the tables with data from the database.
//     */
//    private void populate() {
//        if (listOfPapers != null) {
//            for (Paper p : listOfPapers) {
//                conferencePapersTable.add(new PaperRow(p.getPaperID(), p.getTitle(), p.getSubmissionDate()));
//            }
//            conferencePapersTable.updateItems();
//        }
//        if (listOfUsers != null) {
//            for (ConferenceUser u : listOfUsers) {
//                conferenceUsersTable.add(new ConferenceUserRow(u.getUserID(), u.getUsername(), u.getRole()));
//            }
//            conferenceUsersTable.updateItems();
//        }
//    }
//    
//    /**
//     * Event handler for handling table and button click events.
//     */
//    @Override
//    public void handle(final Event event) {
//        Object source = event.getSource();
//        if (source == conferencePapersTable) {
//            MouseEvent mouseEvent = (MouseEvent) event;
//            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
//                int paperID = conferencePapersTable.getSelectionModel()
//                                                   .getSelectedItem()
//                                                   .getId();
//            }
//        }
//        if (source == conferenceUsersTable) {
//            MouseEvent mouseEvent = (MouseEvent) event;
//            if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
//                int userID = conferenceUsersTable.getSelectionModel()
//                                                 .getSelectedItem()
//                                                 .getID();
//            }
//        }
//        
//        if (source == addReviewerButton) {
//            new UsersPane(callbacks.getPrimaryStage(), progressSpinnerCallbacks, this).showDialog();
//        }
//        
//        if (source == addSubprogramChairButton) {
//            //TODO finish
//        }
//        if (source == uploadPaperButton) {
//            mainPaneCallbacks.pushPane(new UploadPaperPane(conferenceID, callbacks, mainPaneCallbacks, progressSpinnerCallbacks));
//        }
//    }
//    
//    @Override
//    public void addReviewer(final int userID) {
//        new AddUserService(progressSpinnerCallbacks, conferenceID, userID, PermissionLevel.REVIEWER).start();
//    }
//    
//    /**
//     * Adds a user to the conference
//     * 
//     * 
//     */
//    private class AddUserService extends ProgressSpinnerService {
//        
//        private int conferenceID;
//        private int userID;
//        private PermissionLevel permission;
//        
//        public AddUserService(final ProgressSpinnerCallbacks progressSpinnerCallbacks, final int ConferenceID, final int UserID,
//                final PermissionLevel permission) {
//            super(progressSpinnerCallbacks);
//            
//            this.conferenceID = ConferenceID;
//            this.userID = UserID;
//            this.permission = permission;
//        }
//        
//        @Override
//        protected Task<String> createTask() {
//            return new Task<String>() {
//                
//                /**
//                 * Calls the new task.
//                 */
//                @Override
//                protected String call() {
//                    try {
//                        ConferenceManager.addUserToConference(conferenceID, userID, permission);
//                        setSuccess(true);
//                        System.out.println("adding " + userID);
//                    }
//                    catch (Exception e) {
//                        //TODO make sure message dialog works
//                        new MessageDialog(callbacks.getPrimaryStage()).showDialog(e.getMessage(), false);
//                        
//                    }
//                    return null;
//                }
//            };
//        }
//        
//        @Override
//        protected void succeeded() {
//            if (getSuccess()) {
//                //  populate();
//            }
//            super.succeeded();
//        }
//    }
//    
//    /**
//     * Loads conference, paper, and review data from database.
//     */
//    private class LoadDataService extends ProgressSpinnerService {
//        
//        /**
//         * Creates a new LoadDataService.
//         * 
//         * @param progressSpinnerCallbacks Spinner that spins during database query.
//         */
//        public LoadDataService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
//            super(progressSpinnerCallbacks);
//        }
//        
//        /**
//         * Creates a new task for loading table lists.
//         */
//        @Override
//        protected Task<String> createTask() {
//            return new Task<String>() {
//                
//                /**
//                 * Calls the new task.
//                 */
//                @Override
//                protected String call() {
//                    try {
//                        listOfPapers = PaperManager.getPapers(conferenceID);
//                        listOfUsers = ConferenceManager.getUsersInConference(conferenceID);
//                        
//                        setSuccess(true);
//                    }
//                    catch (Exception e) {
//                        // TODO show error
//                    }
//                    return null;
//                }
//            };
//        }
//        
//        /**
//         * Called when data loading is done to populate tables
//         */
//        @Override
//        protected void succeeded() {
//            if (getSuccess()) {
//                populate();
//            }
//            super.succeeded();
//        }
//    }