
package view.conferences;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.conferences.Conference;
import model.conferences.ConferenceManager;
import view.util.GenericPane;

/**
 * JavaFX pane responsible for displaying all conferences in the database.
 * 
 * @author Mohammad Juma
 * @version 11-23-2013
 */
public class ConferencesPane extends GenericPane<GridPane> {
    
    /**
     * The conference list TableView.
     */
    private TableView<ConferenceRow> table;
    
    /**
     * An observable list for the conferences TableView.
     */
    private ObservableList<ConferenceRow> data;
    
    /**
     * Column names of conferences TableView.
     */
    private String[] columnNames = { "Conference Name", "Program Chair", "Authors", "Reviewers", "Date" };
    
    /**
     * The Database variables used to populate the conferences TableView.
     */
    private String[] variableNames = { "name", "programChair", "authors", "reviwers", "date" };
    
    /**
     * The widths of the conferences TableView.
     */
    private Integer[] columnWidths = { 30, 25, 10, 10, 25 };
    
    /**
     * The list of conferences.
     */
    private List<Conference> conferences;
    
    /**
     * After selecting a conference in the table this button will take you to that conferences
     * page.
     */
    private Button viewConferenceButton;
    
    /**
     * Allows a user to add a conference and become its program chair.
     */
    private Button addConferenceButton;
    
    /**
     * Constructs a new HomePane pane that extends GridPane and displays the initial user
     * interface the user is greeted with upon login in.
     */
    public ConferencesPane() {
        super(new GridPane());
        table = new TableView<ConferenceRow>();
        data = FXCollections.observableArrayList();
        
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 5, 5, 5));
        
        create();
    }
    
    /**
     * Creates the main components of the HomePane pane.
     */
    private void create() {
        populateTable();
        
        Text titleText = new Text("Conferences");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        pane.add(titleText, 0, 0);
        pane.add(table, 0, 1);
        
        viewConferenceButton = new Button("View Conference");
        
        addConferenceButton = new Button("Add Conference");
        
        final HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_LEFT);
        buttonHBox.getChildren()
                  .add(viewConferenceButton);
        buttonHBox.getChildren()
                  .add(addConferenceButton);
        pane.add(buttonHBox, 0, 2);
    }
    
    /**
     * Populates the conference table.
     */
    private void populateTable() {
        
        TableColumn<ConferenceRow, String> column;
        for (int i = 0; i < columnNames.length; i++) {
            column = new TableColumn<ConferenceRow, String>(columnNames[i]);
            // column.setMinWidth(columnWidths[i]);
            column.prefWidthProperty()
                  .bind(table.widthProperty()
                             .divide(100 / columnWidths[i]));
            
            column.setCellValueFactory(new PropertyValueFactory<ConferenceRow, String>(variableNames[i]));
            
            table.getColumns()
                 .add(column);
        }
        
        conferences = ConferenceManager.getConferences();
        for (Conference c : conferences) {
            data.add(new ConferenceRow(c.getID(), c.getName(), c.getLocation(), c.getDate(), c.getProgramChair(), c.getAuthors(), c.getReviewers()));
        }
        
        table.setItems(data);
    }
}
