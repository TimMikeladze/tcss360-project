
package model.database;

import java.sql.SQLException;

/**
 * Class for Database Exceptions.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class DatabaseException extends SQLException {
    
    /**
     * The serial version of the class.
     */
    private static final long serialVersionUID = -6252031987727266735L;
    
    /**
     * Creates a new Database Exception.
     * 
     * <dt><b>Precondition:</b><dd> requires errors != null;
     * <dt><b>Postcondition:</b><dd> ensures The proper error is returned.
     * @param error the error
     */
    public DatabaseException(final DatabaseErrors error) {
        super(error.getError());
    }
}
