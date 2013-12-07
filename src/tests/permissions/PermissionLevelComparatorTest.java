package tests.permissions;

import static org.junit.Assert.*;

import model.permissions.PermissionLevel;
import model.permissions.PermissionLevelComparator;

import org.junit.Test;

/**
 * Tests the PermissionLevelComparator class from model.permissions
 * 
 * @author Jordan Matthews
 * @version December 5, 2013
 */
public class PermissionLevelComparatorTest {
    
    /**
     * Tests the comparator
     */
    @Test
    public void testCompare() {
        PermissionLevelComparator temp = new PermissionLevelComparator();
        assertEquals("compare", temp.compare(PermissionLevel.REVIEWER, PermissionLevel.AUTHOR), 100);
    }

}
