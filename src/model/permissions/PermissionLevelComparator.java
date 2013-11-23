
package model.permissions;

import java.util.Comparator;

public class PermissionLevelComparator implements Comparator<PermissionLevel> {
    
    @Override
    public int compare(final PermissionLevel p1, final PermissionLevel p2) {
        return p1.getPermission() - p2.getPermission();
    }
}
