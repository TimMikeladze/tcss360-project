
package view.util;

import javafx.scene.control.ProgressIndicator;

public class CustomProgressIndicator extends ProgressIndicator {
    
    private static final double SCALE = 1.25;
    
    public CustomProgressIndicator() {
        super();
        create();
    }
    
    public CustomProgressIndicator(final double progress) {
        super(progress);
        create();
    }
    
    private void create() {
        setVisible(false);
        setScaleX(getScaleX() * SCALE);
        setScaleY(getScaleY() * SCALE);
    }
    
}
