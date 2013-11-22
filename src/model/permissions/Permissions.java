
package model.permissions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import model.util.PackageReflector;

/**
 * This class handles permission checks.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class Permissions {
    
    /**
     * A map of the permissions.
     */
    private static final HashMap<Class<?>, HashMap<String, PermissionMethod>> classMap = loadClassMap();
    
    /**
     * Does a permission check.
     * 
     * @param className the class where the method is
     * @param method method trying to access
     * @param permission the permission required to access method
     * @return returns true if can access
     */
    public static boolean hasPermission(final Class<?> className, final String method, final PermissionLevel permission) {
        boolean hasPermission = false;
        int givenPermission = permission.getPermission();
        
        HashMap<String, PermissionMethod> methods = classMap.get(className);
        if (methods != null && methods.containsKey(method)) {
            int methodPermission = methods.get(method)
                                          .getPermission()
                                          .level();
            boolean strict = methods.get(method)
                                    .getPermission()
                                    .strict();
            if ((strict && givenPermission == methodPermission) || (!strict && givenPermission >= methodPermission)) {
                hasPermission = true;
            }
        }
        return hasPermission;
    }
    
    /**
     * Loads all a map of classes and methods which contain the the Permission annotation.
     * 
     * @return A map of all the classes and methods which contain Permission annotation
     */
    private static HashMap<Class<?>, HashMap<String, PermissionMethod>> loadClassMap() {
        HashMap<Class<?>, HashMap<String, PermissionMethod>> map = new HashMap<Class<?>, HashMap<String, PermissionMethod>>();
        try {
            List<Class<?>> classes = PackageReflector.getClasses("model");
            for (Class<?> c : classes) {
                HashMap<String, PermissionMethod> methods = new HashMap<String, PermissionMethod>();
                for (Method m : c.getMethods()) {
                    Permission permission = m.getAnnotation(Permission.class);
                    if (permission != null) {
                        methods.put(m.getName(), new PermissionMethod(m, permission));
                    }
                }
                map.put(c, methods);
            }
            
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
