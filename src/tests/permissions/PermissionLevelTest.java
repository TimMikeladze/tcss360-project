package tests.permissions;

import static org.junit.Assert.*;

import model.permissions.PermissionLevel;

import org.junit.Test;
/**
 * Tests the PermissionLevel class
 * 
 * @author Jordan Matthews
 * @version 1.0
 */
public class PermissionLevelTest {
    /**
     * Value of the author
     */
    private final static int AUTHOR = 100;
    /**
     * Value of reviewer
     */
    private final static int REVIEWER = 200;
    /**
     * Value of subprogram chair
     */
    private final static int SUBPROGRAM = 300;
    /**
     * Value of program chair
     */
    private final static int PROGRAM = 400;
    /**
     * Value of admin
     */
    private final static int ADMIN = 500;
    /**
     * The first permission level
     */
    private static PermissionLevel permissionID1;
    /**
     * The second permission level
     */
    private static PermissionLevel permissionID2;
    /**
     * The third permission level
     */
    private static PermissionLevel permissionID3;
    /**
     * The fourth permission level
     */
    private static PermissionLevel permissionID4;
    /**
     * The fifth permission level
     */
    private static PermissionLevel permissionID5;
    
    /**
     * Test of the getPermissions() method
     */
    @Test
    public void testGetPermissions() {
        permissionID1 = PermissionLevel.AUTHOR;
        permissionID2 = PermissionLevel.REVIEWER;
        permissionID3 = PermissionLevel.SUBPROGRAM_CHAIR;
        permissionID4 = PermissionLevel.PROGRAM_CHAIR;
        permissionID5 = PermissionLevel.ADMIN;
        
        assertEquals("Permission Author is correct", AUTHOR, permissionID1.getPermission());
        assertEquals("Permission Reviewer is correct", REVIEWER, permissionID2.getPermission());
        assertEquals("Permission Subprogram chair is correct", SUBPROGRAM, permissionID3.getPermission());
        assertEquals("Permission Program chair is correct", PROGRAM, permissionID4.getPermission());
        assertEquals("Permission Admin is correct", ADMIN, permissionID5.getPermission());
    }

}
