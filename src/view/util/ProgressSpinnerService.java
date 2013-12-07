
package view.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * The ProgressSpinnerService class, manages a service and a progress spinner.
 * @author Srdjan
 * @version 11-14-13
 */
public abstract class ProgressSpinnerService extends Service<String> {
    
    protected ProgressSpinnerCallbacks progressSpinnerCallbacks;
    private boolean success;
    
    /**
     * Instantiates a new progress spinner service.
     * 
     * @param progressSpinnerCallbacks the progress spinner callbacks
     */
    public ProgressSpinnerService(final ProgressSpinnerCallbacks progressSpinnerCallbacks) {
        this.progressSpinnerCallbacks = progressSpinnerCallbacks;
    }
    
    @Override
    public void start() {
        super.start();
    }
    
    @Override
    protected void succeeded() {
        super.succeeded();
        progressSpinnerCallbacks.stop();
    }
    
    /**
     * Creates the progress service task.
     * 
     * @return the task
     */
    @Override
    protected abstract Task<String> createTask();
    
    @Override
    protected void executeTask(final Task<String> task) {
        progressSpinnerCallbacks.bindTask(task);
        progressSpinnerCallbacks.start();
        super.executeTask(task);
    }
    
    @Override
    protected void cancelled() {
        super.cancelled();
        progressSpinnerCallbacks.stop();
    }
    
    @Override
    protected void failed() {
        super.failed();
        progressSpinnerCallbacks.stop();
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
}
