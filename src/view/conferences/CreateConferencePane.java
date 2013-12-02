
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
import view.util.Callbacks;
import view.util.GenericPane;
import view.util.MainPaneCallbacks;
import view.util.ProgressSpinnerCallbacks;
import view.util.ProgressSpinnerService;
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
     * Create Conference Text.
     */
    private Text createConferenceText;
    
    /**
     * Conference Name Label.
     */
    private Label conferenceNameLabel;
    
    /**
     * Conference Name TextField.
     */
    private TextField conferenceNameTextField;
    
    /**
     * Conference Location Label.
     */
    private Label conferenceLocationLabel;
    
    /**
     * Conference Location TextField.
     */
    private TextField conferenceLocationTextField;
    
    /**
     * Conference Date Label.
     */
    private Label conferenceDateLabel;
    
    /**
     * Conference DatePicker.
     */
    private DatePicker datePicker;
    
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
     * @param callbacks Callback for the stage of the application.
     * @param mainPaneCallbacks Callback for the center pane of the main pane.
     * @param progressSpinnerCallbacks Callback for the progress spinner.
     */
    public CreateConferencePane(final Callbacks callbacks, final MainPaneCallbacks mainPaneCallbacks,
            final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        super(new GridPane());
        addCallbacks(callbacks);
        addMainPaneCallBacks(mainPaneCallbacks);
        addProgressSpinnerCallBacks(progressSpinnerCallbacks);
        
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        
        create();
    }
    
    @Override
    public GenericPane<GridPane> refresh() {
        return new CreateConferencePane(callbacks, mainPaneCallbacks, progressSpinnerCallbacks);
    }
    
    /**
     * Creates the components of this pane.
     * 
     * @author Mohammad Juma
     */
    private void create() {
        createConferenceText = new Text("Create Conference");
        createConferenceText.setId("header1");
        createConferenceText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(createConferenceText, 0, 0);
        
        conferenceNameLabel = new Label("Name:");
        pane.add(conferenceNameLabel, 0, 1);
        
        conferenceNameTextField = new TextField();
        pane.add(conferenceNameTextField, 1, 1);
        
        conferenceLocationLabel = new Label("Location:");
        pane.add(conferenceLocationLabel, 0, 2);
        
        conferenceLocationTextField = new TextField();
        pane.add(conferenceLocationTextField, 1, 2);
        
        conferenceDateLabel = new Label("Date:");
        pane.add(conferenceDateLabel, 0, 3);
        
        datePicker = new DatePicker();
        datePicker.localeProperty()
                  .set(Locale.US);
        datePicker.getCalendarView()
                  .todayButtonTextProperty()
                  .set("Today");
        datePicker.getCalendarView()
                  .setShowWeeks(false);
        callbacks.getScene()
                 .getStylesheets()
                 .add("view/styling/calendarstyle.css");
        datePicker.selectedDateProperty()
                  .addListener(new InvalidationListener() {
                      
                      @Override
                      public void invalidated(final Observable observable) {
                          Date selected = datePicker.selectedDateProperty()
                                                    .get();
                          System.out.println(selected);
                          conferenceDate = new Timestamp(selected.getTime());
                          System.out.println(conferenceDate);
                      }
                  });
        pane.add(datePicker, 1, 3);
        
        createConferenceButton = new Button("Create");
        
        HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        
        buttonHBox.getChildren()
                  .add(createConferenceButton);
        
        pane.add(buttonHBox, 1, 5);
        
        statusText = new StatusText();
        pane.add(statusText, 1, 6);
        
        createConferenceButton.setOnAction(this);
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
        String name = conferenceNameTextField.getText();
        String location = conferenceLocationTextField.getText();
        
        if (!Validator.isEmpty(name, location, conferenceDate.toString())) {
            new CreateConferenceService(progressSpinnerCallbacks, name, location, conferenceDate).start();
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
        
        private int conferenceID;
        
        /**
         * Creates a new conference.
         * 
         * @param progressSpinnerCallbacks The progress spinner
         * @param conferenceName The conference name
         * @param conferenceLocation The conference location
         * @param conferenceDate The conference date
         */
        public CreateConferenceService(final ProgressSpinnerCallbacks progressSpinnerCallbacks, final String conferenceName,
                final String conferenceLocation, final Timestamp conferenceDate) {
            super(progressSpinnerCallbacks);
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
                        int userID = LoggedUser.getInstance()
                                               .getUser()
                                               .getID();
                        
                        conferenceID = ConferenceManager.createConference(conferenceName, conferenceLocation, conferenceDate, userID);
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
                mainPaneCallbacks.pushPane(new ConferencePane(conferenceID, callbacks, mainPaneCallbacks, progressSpinnerCallbacks));
            }
            super.succeeded();
        }
    }
    
}
