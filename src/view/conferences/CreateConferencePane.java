
package view.conferences;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.conferences.ConferenceManager;
import view.util.CenterPaneCallbacks;
import view.util.GenericPane;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
import view.util.SceneCallbacks;
import view.util.StatusText;
import view.util.Validator;
import view.util.calendar.DatePicker;
import controller.user.LoggedUser;

/**
 * JavaFX pane responsible for displaying the pane that allows a user to create a new
 * conference.
 * 
 * @author Tim Mikeladze
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class CreateConferencePane extends GenericPane<GridPane> implements EventHandler<ActionEvent> {
    
    /**
     * Conference Name TextField.
     */
    private TextField conferenceNameTextField;
    
    /**
     * Conference Location TextField.
     */
    private TextField conferenceLocationTextField;
    
    /**
     * Create Conference Button.
     */
    private Button createConferenceButton;
    
    /**
     * Conference Creation Status Text.
     */
    private StatusText statusText;
    
    /**
     * The date of the conference to be created.
     */
    private Timestamp conferenceDate;
    
    /**
     * Creates a new CreateConferencePane to allow the user to create a new conference.
     * 
     * @param sceneCallback A callback to the scene this pane is in
     * @param centerPaneCallback A callback to the center pane
     * @param progressSpinnerCallback A callback to the progress spinner
     */
    public CreateConferencePane(final SceneCallbacks callbacks, final CenterPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane());
        addSceneCallback(callbacks);
        addCenterPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBack(progressSpinnerCallbacks);
        
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        
        create();
    }
    
    /**
     * Refreshes the current pane after data has been changed.
     */
    @Override
    public GenericPane<GridPane> refresh() {
        return new CreateConferencePane(sceneCallback, centerPaneCallback, progressSpinnerCallback);
    }
    
    /**
     * Creates the components of this pane.
     * 
     * @author Mohammad Juma
     */
    private void create() {
        final Text createConferenceText = new Text("Create Conference");
        createConferenceText.setId("header1");
        createConferenceText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(createConferenceText, 0, 0);
        
        final Label conferenceNameLabel = new Label("Name:");
        pane.add(conferenceNameLabel, 0, 1);
        
        conferenceNameTextField = new TextField();
        pane.add(conferenceNameTextField, 1, 1);
        
        final Label conferenceLocationLabel = new Label("Location:");
        pane.add(conferenceLocationLabel, 0, 2);
        
        conferenceLocationTextField = new TextField();
        pane.add(conferenceLocationTextField, 1, 2);
        
        final Label conferenceDateLabel = new Label("Date:");
        pane.add(conferenceDateLabel, 0, 3);
        
        final DatePicker datePicker = new DatePicker();
        datePicker.localeProperty().set(Locale.US);
        datePicker.getCalendarView().todayButtonTextProperty().set("Today");
        datePicker.getCalendarView().setShowWeeks(false);
        sceneCallback.getScene().getStylesheets().add("view/styling/calendarstyle.css");
        datePicker.selectedDateProperty().addListener(new InvalidationListener() {
            
            @Override
            public void invalidated(final Observable observable) {
                final Date selected = datePicker.selectedDateProperty().get();
                System.out.println(selected);
                conferenceDate = new Timestamp(selected.getTime());
                System.out.println(conferenceDate.toString().split("\\s+")[0].toString());
            }
        });
        pane.add(datePicker, 1, 3);
        
        createConferenceButton = new Button("Create");
        createConferenceButton.setOnAction(this);
        
        final HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHBox.getChildren().add(createConferenceButton);
        pane.add(buttonHBox, 1, 5);
        
        statusText = new StatusText();
        pane.add(statusText, 1, 6);
    }
    
    /**
     * Event handler for createConferenceButton.
     */
    @Override
    public void handle(final ActionEvent event) {
        if (event.getSource() == createConferenceButton) {
            createConference();
        }
    }
    
    /**
     * Creates a new conference once all the fields have been properly filled in.
     */
    private void createConference() {
        final String name = conferenceNameTextField.getText();
        final String location = conferenceLocationTextField.getText();
        
        if (!Validator.isEmpty(name, location, conferenceDate.toString())) {
            new CreateConferenceService(progressSpinnerCallback, name, location, conferenceDate).start();
        }
        else {
            statusText.setErrorText("Missing field");
        }
    }
    
    /**
     * A service class that runs on its own thread and queries the database to create a new
     * conference.
     * 
     * @author Tim Mikeladze
     * @version 11-23-2013
     */
    private class CreateConferenceService extends ProgressSpinnerService {
        
        /**
         * The conference name.
         */
        private final String conferenceName;
        
        /**
         * The conference location.
         */
        private final String conferenceLocation;
        
        /**
         * The conference date.
         */
        private final Timestamp conferenceDate;
        
        /**
         * The conference id.
         */
        private int conferenceID;
        
        /**
         * Creates a new conference.
         * 
         * @param progressSpinnerCallback The progress spinner
         * @param conferenceName The conference name
         * @param conferenceLocation The conference location
         * @param conferenceDate The conference date
         */
        public CreateConferenceService(final ProgressSpinnerCallbacks progressSpinnerCallback,
                final String conferenceName, final String conferenceLocation, final Timestamp conferenceDate) {
            super(progressSpinnerCallback);
            this.conferenceName = conferenceName;
            this.conferenceLocation = conferenceLocation;
            this.conferenceDate = conferenceDate;
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
                        int userID = LoggedUser.getInstance().getUser().getID();
                        
                        conferenceID = ConferenceManager.createConference(conferenceName, conferenceLocation,
                                conferenceDate, userID);
                        setSuccess(true);
                    }
                    catch (Exception e) {
                        statusText.setSuccessText("Error creating conference");
                    }
                    return null;
                }
            };
        }
        
        /**
         * Called when a conference loading is done to populate table
         */
        @Override
        protected void succeeded() {
            if (getSuccess()) {
                centerPaneCallback.pushPane(new ConferencePane(conferenceID, sceneCallback, centerPaneCallback,
                        progressSpinnerCallbacks));
            }
            super.succeeded();
        }
    }
}
