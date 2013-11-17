package view.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

public interface Callbacks {
	public void changeScene(GenericPane<?> genericPane);

	public void changeScene(GenericPane<?> genericPane, int width, int height);

	public Scene getScene();

	public Stage getPrimaryStage();

}
