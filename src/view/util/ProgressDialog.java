package view.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is a progress dialog, it shows a progress dialog which is updated via a service
 */
public class ProgressDialog extends Stage {
	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 100;
	private Service<?> service;
	private BorderPane root;
	private ProgressIndicator indicator;
	private boolean cancellable;
	private Scene scene;

	public ProgressDialog(Service<?> service, Stage owner, int width, int height) {
		this.service = service;

		root = new BorderPane();
		indicator = new ProgressIndicator();
		scene = new Scene(root, width, height);

		initModality(Modality.WINDOW_MODAL);
		initOwner(owner);

	}

	public ProgressDialog(Service<?> service, Stage owner) {
		this(service, owner, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Sets the whether the dialog and service and be cancelled via a button.
	 * 
	 * @param cancellable
	 *            is cancellable
	 */
	public void setCancellable(boolean cancellable) {
		this.cancellable = cancellable;
	}

	/**
	 * Creates and shows the dialog
	 */
	public void showDialog() {
		indicator.progressProperty().bind(service.progressProperty());
		service.stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue == State.CANCELLED || newValue == State.FAILED || newValue == State.SUCCEEDED) {
					ProgressDialog.this.hide();
				}
			}
		});

		if (cancellable) {
			Button button = new Button("Cancel");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					service.cancel();
				}
			});
			root.setBottom(button);
		}

		root.setCenter(indicator);
		setScene(scene);
		show();
	}

}
