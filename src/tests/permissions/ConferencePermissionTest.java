package tests.permissions;

import static org.junit.Assert.*;

import model.permissions.ConferencePermission;

import org.junit.Test;

/**
 * Tests the ConferencePermissions class from model.permissions
 * 
 * @author Jordan Matthews
 * @version December 5, 2013
 */
public class ConferencePermissionTest {

    /**
     * Tests the getters from the class    
     */
    @Test
    public void testGetters() {
        ConferencePermission temp = new ConferencePermission();
        assertEquals("", temp.getConferenceID(), 0);
        assertEquals("", temp.getPermissionID(), 0);
        assertEquals("", temp.getRole(), "");
        assertEquals("", temp.getUserID(), 0);
    }

}
