package model.conferences;

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
import view.home.HomePane;
import view.util.Callbacks;
import view.util.GenericPane;
import view.util.MainPaneCallbacks;
import view.util.ProgressSpinnerCallbacks;
import view.util.StatusText;


public class CreateConferencePane extends GenericPane<GridPane> implements EventHandler<ActionEvent>{
    private Text scenetitle;
    private Label confereceNameLabel;
    private TextField conferenceNameTextField;
    private Label conferenceLocationLabel;
    private TextField conferenceLocationTextField;
    private Label conferenceDateLabel;
    private TextField confereceDateTextField;
    private Button createConferenceButton;
    private Button cancelButton;
    private StatusText statusText;

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

    private void create() {
        scenetitle = new Text("Create Conference");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        pane.add(scenetitle, 0, 0);

        confereceNameLabel = new Label("Name:");
        pane.add(confereceNameLabel, 0, 1);

        conferenceNameTextField = new TextField();
        pane.add(conferenceNameTextField, 1, 1);

        conferenceLocationLabel = new Label("Location:");
        pane.add(conferenceLocationLabel, 0, 2);

        conferenceLocationTextField = new TextField();
        pane.add(conferenceLocationTextField, 1, 2);

        conferenceDateLabel = new Label("Date:");
        pane.add(conferenceDateLabel, 0, 3);

        confereceDateTextField = new TextField();
        pane.add(confereceDateTextField, 1, 3);

        createConferenceButton = new Button("Create");
        cancelButton = new Button("Go Back");

        HBox buttonHBox = new HBox(10);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);

        buttonHBox.getChildren().add(cancelButton);

        buttonHBox.getChildren().add(createConferenceButton);

        pane.add(buttonHBox, 1, 5);

        statusText = new StatusText();
        pane.add(statusText, 1, 6);

        createConferenceButton.setOnAction(this);
        cancelButton.setOnAction(this);
    }

    @Override
    public void handle(final ActionEvent event) {
        if(event.getSource() == cancelButton) {
            mainPaneCallbacks.changeCenterPane(new HomePane(mainPaneCallbacks, progressSpinnerCallbacks));
        }
    }

}
