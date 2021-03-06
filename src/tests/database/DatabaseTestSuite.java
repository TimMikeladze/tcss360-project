package tests.database;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   DatabaseExceptionTest.class,
   DatabaseTest.class,
   ErrorsTest.class
})
/**
 * Suite of Tests for the model.Database Package
 * 
 * @author Jordan Matthews
 * @version December 4, 2013
 */
public class DatabaseTestSuite {
}