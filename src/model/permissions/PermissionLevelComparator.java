
package model.permissions;

import java.util.Comparator;

/**
 * Comparator
 * @author Tim Mikeladze
 *
 */
public class PermissionLevelComparator implements Comparator<PermissionLevel> {
    
    /**
     * Compares
     */
    @Override
    public int compare(final PermissionLevel p1, final PermissionLevel p2) {
        return p1.getPermission() - p2.getPermission();
    }
}
