package tests.database;

import static org.junit.Assert.*;

import model.database.DatabaseException;
import model.database.Errors;

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
        assertTrue("Exception created", Errors.CANT_ASSIGN_PAPER.toString() != null);
    }

}
