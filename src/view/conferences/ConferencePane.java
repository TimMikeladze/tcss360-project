
package view.conferences;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.conferences.Conference;
import view.users.UserRow;
import view.util.CustomTable;
import view.util.GenericPane;

/**
 * JavaFX pane responsible for displaying information about a selected conference.
 * 
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class ConferencePane extends GenericPane<GridPane> {
    
    /**
     * Column names of conference papers TableView.
     */
    private final String[] conferencePapersColumnolumnNames = { "Paper Name", "Author", "Date" };
    
    /**
     * The Database variables used to populate the conference papers TableView.
     */
    private final String[] conferencePapersVariableNames = { "title", "author", "date" };
    
    /**
     * Column names of conference users TableView.
     */
    private final String[] conferenceUsersColumnNames = { "First Name", "Last Name", "Role" };
    
    /**
     * The Database variables used to populate the conference users TableView.
     */
    private final String[] conferenceUsersVariableNames = { "firstName", "lastName", "role" };
    
    /**
     * The name of the conference.
     */
    private final Text conferenceNameText;
    
    /**
     * The location of the conference.
     */
    private final Text conferenceLocationText;
    
    /**
     * The date of the conference.
     */
    private final Text conferenceDateText;
    
    /**
     * The program chair of the conference.
     */
    private final Text conferenceProgramChairText;
    
    /**
     * The number of authors in the conference.
     */
    private final Text authorsText;
    
    /**
     * The number of reviewers in the conference.
     */
    private final Text reviewersText;
    
    /**
     * List of papers in the conference.
     */
    private TableView conferencePapersTable;
    
    /**
     * List of users in the conference.
     */
    private TableView conferenceUsersTable;
    
    private Button removeConferenceButton;
    
    private Button addSubprogramChairButton;
    
    private Button addReviewerButton;
    
    private Button uploadPaperButton;
    
    private Button uploadReviewButton;
    
    private Button viewPaperButton;
    
    private Button viewUserButton;
    
    /**
     * Constructs a new Conference Pane that extends GridPane and displays the information about
     * the given conference.
     */
    public ConferencePane(final Conference conference) {
        super(new GridPane());
        
        conferenceNameText = new Text("Conference: " + conference.getName());
        conferenceLocationText = new Text("Location: " + conference.getLocation());
        conferenceDateText = new Text("Date: " + conference.getDate().toString());
        conferenceProgramChairText = new Text("Program Chair: " + conference.getProgramChair());
        authorsText = new Text("Authors: " + Integer.toString(conference.getAuthors()));
        reviewersText = new Text("Reviewers: " + Integer.toString(conference.getReviewers()));
        
        conferencePapersTable = new CustomTable<ConferenceRow>(
                conferencePapersColumnolumnNames, conferencePapersVariableNames);
        conferenceUsersTable = new CustomTable<UserRow>(conferenceUsersColumnNames,
                conferenceUsersVariableNames);
        
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));
        
        create();
    }
    
    /**
     * Creates the main components of the ConferencePane pane.
     */
    private void create() {
        pane.add(conferenceNameText, 0, 0);
        pane.add(conferenceLocationText, 1, 0);
        pane.add(conferenceDateText, 0, 1);
        pane.add(conferenceProgramChairText, 1, 1);
        pane.add(authorsText, 0, 2);
        pane.add(reviewersText, 1, 2);
        
        Text conferencePapersText = new Text("Conference Papers");
        conferencePapersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(conferencePapersText, 0, 3);
        conferencePapersTable = new TableView();
        pane.add(conferencePapersTable, 0, 4);
        
        Text conferenceUsersText = new Text("Conference Users");
        conferenceUsersText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(conferenceUsersText, 0, 5);
        conferenceUsersTable = new TableView();
        pane.add(conferenceUsersTable, 0, 6);
        
    }
}
