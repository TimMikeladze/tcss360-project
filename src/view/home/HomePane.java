/*
 * Mohammad Juma
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 11, 2013
 */

package view.home;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.util.GenericPane;

/**
 * JavaFX pane responsible for displaying the users home interface.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class HomePane extends GenericPane<GridPane> {

	TableView<TempTable> table;

	/**
	 * Constructs a new HomePane pane that extends GridPane and displays the
	 * initial user interface the user is greeted with upon login in.
	 */
	public HomePane() {
		super(new GridPane());
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

		table = new TableView<TempTable>();

		table.getColumns().addAll(TempTable.getColumn(table));

		table.setItems(getTable());

		Text titleText = new Text("Conferences");
		titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		pane.add(titleText, 0, 0);

		pane.add(table, 0, 1);

		/*
		 * for (i = 0 i < numberOfComponents; i++) { if
		 * (component[1].getMinPermission() <= theUser.getPermission()) {
		 * pane.add(component[i]); } }
		 */

		/*
		 * TextArea activitiesTextArea = new
		 * TextArea("Example Activity 1\nExample Activity 2\netc...");
		 * //activitiesTextArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		 * pane.add(activitiesTextArea, 0, 1);
		 */
	}

	public static ObservableList<TempTable> getTable() {
		ObservableList<TempTable> data = FXCollections.observableArrayList();
		data.add(new TempTable("TCSS 305", "Daniel Zimmerman", 1, 74,
				new Date()));
		data.addAll(new TempTable("TCSS 342", "Alan Fowler", 3, 32, new Date()));
		data.addAll(new TempTable("TCSS 360", "The Queen", 6, 42, new Date()));

		return data;
	}
}
