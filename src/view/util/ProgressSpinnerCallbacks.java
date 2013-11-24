
package view.util;

import javafx.concurrent.Task;

public interface ProgressSpinnerCallbacks {
    
    public void stop();
    
    public void start();
    
    public void bindTask(Task<?> task);
}
