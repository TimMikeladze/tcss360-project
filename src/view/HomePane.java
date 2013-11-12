/*
 * Mohammad Juma
 * 
 * TCSS 360 - Winter 2013
 * TCSS 360 Project
 * November 11, 2013
 */

package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * JavaFX pane responsible for displaying the users home interface.
 * 
 * @author Mohammad Juma
 * @version 11-11-2013
 */
public class HomePane extends GridPane {

    /**
     * Constructs a new HomePane pane that extends GridPane and displays the
     * initial user interface the user is greeted with upon login in.
     */
    public HomePane() {
        this.setAlignment(Pos.TOP_LEFT);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        create();
    }
    
    /**
     * Creates the main components of the HomePane pane.
     */
    private void create() {
        Text titleText = new Text("Latest Activites");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.add(titleText, 0, 0);
        
        TextArea activitiesTextArea = new TextArea("Example Activity 1\nExample Activity 2\netc...");
        //activitiesTextArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.add(activitiesTextArea, 0, 1);
    }
}
