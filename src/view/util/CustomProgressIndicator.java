
package view.util;

import javafx.scene.control.ProgressIndicator;

/**
 * A custom progress indicator used throughout the program.
 * @author Cathryn
 * @version 12-01-13
 */
public class CustomProgressIndicator extends ProgressIndicator {
    
    private static final double SCALE = 1.00;
    
    /**
     * Instantiates a new custom progress indicator.
     */
    public CustomProgressIndicator() {
        super();
        create();
    }
    
    /**
     * Instantiates a new custom progress indicator.
     * 
     * @param progress the progress
     */
    public CustomProgressIndicator(final double progress) {
        super(progress);
        create();
    }
    
    /**
     * Sets the properties of the progress indicator
     */
    private void create() {
        setVisible(false);
        setScaleX(getScaleX() * SCALE);
        setScaleY(getScaleY() * SCALE);
    }
    
}
