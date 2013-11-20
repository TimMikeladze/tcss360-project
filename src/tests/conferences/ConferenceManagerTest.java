
package tests.conferences;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;

import model.conferences.ConferenceManager;
import model.database.Database;
import model.database.DatabaseException;
import model.permissions.PermissionLevel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.data.Table;

public class ConferenceManagerTest {
    
    //1384923537242
    private int id;
    private static PermissionLevel permissionID;
    private static int programChairID;
    private static String name;
    private static String location;
    private static Timestamp date;
    private static int userID = 2;
    private static int userIDForConference = 3;
    
    @BeforeClass
    public static void setup() {
        name = "Test Conference";
        location = "Test conference location";
        date = new Timestamp(Long.valueOf("1384923537242"));
        programChairID = 1;
        permissionID = PermissionLevel.SUBPROGRAM_CHAIR;
    }
    
    @AfterClass
    public static void cleanup() {
        
    }
    
    @Test
    public void testCreateConference() {
        
        id = ConferenceManager.createConference(name, location, date, programChairID);
        System.out.println(id);
        Table t = Database.getInstance()
                .createQuery("SELECT COUNT(1) FROM conferences WHERE ID = :id")
                .addParameter("id", id).executeAndFetchTable();
        assertTrue("Conference created", Database.hasResults(t));
    }
    
    @Test
    public void testAddUserToConference() {
        try {
            System.out.println(id);
            ConferenceManager.addUserToConference(id, userIDForConference, permissionID);
            Table t = Database
                    .getInstance()
                    .createQuery(
                            "SELECT COUNT(1) FROM conference_users WHERE ConferenceID = :id AND UserID = :userID AND PermissionID = :permissionID")
                    .addParameter("id", id).addParameter("userID", userIDForConference)
                    .addParameter("permissionID", permissionID).executeAndFetchTable();
            assertTrue("User added", Database.hasResults(t));
        }
        catch (DatabaseException e) {
            fail("User already exists in conference");
        }
    }
    
    @Test
    public void testRemoveUserFromConference() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testConferenceExists() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testGetUsersInConference() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testGetConferences() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testRemoveConference() {
        
    }
}
