package test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tests.conferences.ConferencesTestSuite;
import tests.database.DatabaseTestSuite;
import tests.login.LoginTestSuite;
import tests.papers.PapersTestSuite;
import tests.permissions.PermissionsTestSuite;
import tests.recommendations.RecommendationsTestSuite;
import tests.reviews.ReviewsTestSuite;
import tests.users.UsersTestSuite;
import tests.util.UtilTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   ReviewsTestSuite.class,
   UsersTestSuite.class,
   UtilTestSuite.class,
   RecommendationsTestSuite.class,
   PermissionsTestSuite.class,
   PapersTestSuite.class,
   LoginTestSuite.class,
   DatabaseTestSuite.class,
   ConferencesTestSuite.class
})
/**
 * Suite of Tests for all of the test packages
 * 
 * @author Jordan Matthews
 * @version December 4, 2013
 */
public class AllTests {
}