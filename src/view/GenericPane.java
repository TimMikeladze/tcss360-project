package view;

import javafx.scene.layout.Pane;

public abstract class GenericPane<T extends Pane> {
	protected Callbacks callbacks;
	protected T pane;

	public GenericPane(T t) {
		this.pane = t;
	}

	public void addCallbacks(Callbacks callbacks) {
		this.callbacks = callbacks;
	}

	public T getPane() {
		return pane;
	}
}
