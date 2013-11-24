
package view.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import view.login.LoginPane;
import view.util.Callbacks;
import view.util.GenericPane;
import controller.user.LoggedUser;

/**
 * Creates a pane that runs horizontal along the windows north border and displays the users
 * name along with the logout button.
 * 
 */
public class TopPane extends GenericPane<StackPane> implements EventHandler<ActionEvent> {
    
    private HBox leftBox;
    private HBox rightBox;
    private Button logoutButton;
    private Label welcomeLabel;
    
    public TopPane(final Callbacks callbacks) {
        super(new StackPane(), callbacks);
        pane.setPadding(new Insets(5, 5, 5, 5));
        leftBox = new HBox(10);
        rightBox = new HBox(10);
        
        create();
    }
    
    private void create() {
        leftBox.setAlignment(Pos.CENTER_LEFT);
        welcomeLabel = new Label("Welcome " + LoggedUser.getInstance()
                                                        .getUser()
                                                        .getFullName());
        leftBox.getChildren()
               .add(welcomeLabel);
        
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        
        rightBox.getChildren()
                .add(new ProgressIndicator());
        
        logoutButton = new Button("Logout");
        logoutButton.setOnAction(this);
        rightBox.getChildren()
                .add(logoutButton);
        
        pane.getChildren()
            .add(leftBox);
        pane.getChildren()
            .add(rightBox);
    }
    
    @Override
    public void handle(final ActionEvent event) {
        if (event.getSource() == logoutButton) {
            LoggedUser.getInstance()
                      .logout();
            callbacks.changeScene(new LoginPane());
        }
    }
    
}
