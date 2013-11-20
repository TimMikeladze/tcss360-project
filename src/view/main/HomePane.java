package view.main;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.conferences.Conference;
import model.conferences.ConferenceManager;
import view.util.GenericPane;

/**
 * JavaFX pane responsible for displaying the users home interface.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class HomePane extends GenericPane<GridPane> {
    
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
    private String[] columnNames = { "Conference Name", "Program Chair", "Authors",
            "Reviewers", "Date" };
    
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
     * Constructs a new HomePane pane that extends GridPane and displays the
     * initial user interface the user is greeted with upon login in.
     */
    public HomePane() {
        super(new GridPane());
        table = new TableView<ConferenceRow>();
        data = FXCollections.observableArrayList();
        
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        
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
                    .bind(table.widthProperty().divide(100 / columnWidths[i]));
            
            column.setCellValueFactory(new PropertyValueFactory<ConferenceRow, String>(
                    variableNames[i]));
            
            table.getColumns().add(column);
        }
        
        conferences = ConferenceManager.getConferences();
        for (Conference c : conferences) {
            data.add(new ConferenceRow(c.getName(), c.getLocation(), c.getDate(), c
                    .getProgramChair().getFullName(), c.getAuthors(), c.getReviewers()));
        }
        
        table.setItems(data);
    }
}
