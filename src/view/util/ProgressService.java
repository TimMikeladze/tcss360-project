
package view.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;

/**
 * This abstract class is a service that shows a progress dialog while running.
 * 
 * @author Srdjan
 * @version 11-17-2013
 */
public abstract class ProgressService extends Service<String> {
    
    /**
     * The stage for the progress service.
     */
    private Stage primaryStage;
    
    /**
     * Boolean for progress status.
     */
    private boolean success;
    
    /**
     * Creates a new progress service.
     * 
     * @param primaryStage
     */
    public ProgressService(final Stage primaryStage) {
        super();
        this.primaryStage = primaryStage;
    }
    
    /**
     * Starts the progress service service GUI
     */
    @Override
    public void start() {
        super.start();
        new ProgressDialog(this, primaryStage).showDialog();
    }
    
    /**
     * Sets the status of the progress service.
     * 
     * @param success the status of the progress service
     */
    public void setSuccess(final boolean success) {
        this.success = success;
    }
    
    /**
     * Gets the status of the progress service.
     * 
     * @return the status of the progress service.
     */
    public boolean getSuccess() {
        return success;
    }
    
    /**
     * Creates the progress service task.
     */
    @Override
    protected abstract Task<String> createTask();
    
}