package tests.permissions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   PermissionLevelTest.class,
   PermissionMethodTest.class,
   PermissionLevelComparatorTest.class,
   ConferencePermissionTest.class,
})
/**
 * Suite of Tests for the model.papers Package
 * 
 * @author Jordan Matthews
 * @version December 4, 2013
 */
public class PermissionsTestSuite {
}