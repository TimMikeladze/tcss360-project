
package tests.database;

import static org.junit.Assert.assertTrue;
import model.database.DatabaseErrors;

import org.junit.Test;

/**
 * Tests the Errors class from models.database
 * 
 * @author Jordan Matthews
 * @version December 5, 2013
 */
public class ErrorsTest {
    
    /**
     * Tests the toString method
     */
    @Test
    public void testErrors() {
        assertTrue("Exception created", DatabaseErrors.CANT_ASSIGN_PAPER.toString() != null);
    }
    
}
