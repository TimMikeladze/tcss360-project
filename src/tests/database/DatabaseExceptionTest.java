
package tests.database;

import static org.junit.Assert.assertTrue;
import model.database.DatabaseErrors;
import model.database.DatabaseException;

import org.junit.Test;

/**
 * Tests the DatabaseException CLass from model.database
 * 
 * @author Jordan Matthews
 * @version December 4, 2013
 *
 */
public class DatabaseExceptionTest {
    
    /**
     * Tests the creating on the object
     */
    @Test
    public void testDataException() {
        DatabaseException temp = new DatabaseException(DatabaseErrors.CANT_ASSIGN_PAPER);
        assertTrue("Exception created", temp != null);
    }
}
