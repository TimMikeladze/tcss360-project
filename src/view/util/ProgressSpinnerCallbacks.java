
package view.util;

import javafx.concurrent.Task;

/**
 * The Interface ProgressSpinnerCallbacks.
 */
public interface ProgressSpinnerCallbacks {
    
    /**
     * Stop the spinner.
     */
    public void stop();
    
    /**
     * Start the spinner.
     */
    public void start();
    
    /**
     * Bind a task to the spinner.
     * 
     * @param task the task
     */
    public void bindTask(Task<?> task);
}
