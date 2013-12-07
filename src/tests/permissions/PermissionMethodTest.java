package tests.permissions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Method;

import model.permissions.Permission;
import model.permissions.PermissionLevel;
import model.permissions.PermissionMethod;
import model.util.PackageReflector;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the PermissionMethod class from model.permissions
 * 
 * @author Srdjan Stojcic
 * @version December 5, 2013
 */
public class PermissionMethodTest {
    /**
     * The permission method object
     */
    private PermissionMethod pm;
    /**
     * The permission method object
     */
    private PermissionMethod pm2;
    /**
     * Sets up the class
     * 
     * @throws ClassNotFoundException exc
     * @throws IOException exc
     */
    @Before
    public void setUp() throws ClassNotFoundException, IOException {
        Class<?>[] classes = PackageReflector.getClasses("model");
        for (Class<?> c : classes) {
            
            for (Method m : c.getMethods()) {
                Permission permission = m.getAnnotation(Permission.class);
                pm = new PermissionMethod(m, permission);
                pm2 = new PermissionMethod(null, permission); 
            }
        }
    }
    /**
     * Tests all the getters
     */
    @Test
    public void testGetters() {
        assertTrue("get method", pm.getMethod() != null);
        assertTrue("get perm", pm.getPermission() == null);
        assertTrue("get hash", pm.hashCode() > 0);
        assertTrue("get hash", pm2.hashCode() > 0);
        assertTrue("get string", pm.toString() != null);
        assertFalse("equals", pm.equals(null));
        assertFalse("equals", pm2.equals(pm));
        assertFalse("equals", !pm.equals(pm));
        assertFalse("equals", pm.equals(""));
    }

}
