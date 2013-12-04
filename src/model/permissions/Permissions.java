
package model.permissions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import model.database.Database;
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
    
    public static List<ConferencePermission> getPermissionsForConference(final int id, final int userID) {
        return Database.getInstance()
                       .createQuery("SELECT ConferenceID, UserID, PermissionID FROM conference_users WHERE ConferenceID = :id AND UserID = :userID")
                       .addParameter("id", id)
                       .addParameter("userID", userID)
                       .executeAndFetch(ConferencePermission.class);
    }
    
    /**
     * Does a permission check.
     * 
     * @param className the class where the method is
     * @param method method trying to access
     * @param permission the permission required to access method
     * @return returns true if can access
     */
    public static boolean hasPermission(final Class<?> className, final String method, final TreeSet<PermissionLevel> permissions) {
        boolean hasPermission = false;
        
        HashMap<String, PermissionMethod> methods = classMap.get(className);
        if (methods != null && methods.containsKey(method)) {
            int methodPermission = methods.get(method)
                                          .getPermission()
                                          .level();
            boolean strict = methods.get(method)
                                    .getPermission()
                                    .strict();
            System.out.println("method permission " + methodPermission);
            if ((strict && permissions.contains(methodPermission))
                    || (!permissions.isEmpty() && !strict && permissions.last()
                                                                        .getPermission() >= methodPermission)) {
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
            Class<?>[] classes = PackageReflector.getClasses("model");
            for (Class<?> c : classes) {
                HashMap<String, PermissionMethod> methods = new HashMap<String, PermissionMethod>();
                for (Method m : c.getMethods()) {
                    Permission permission = m.getAnnotation(Permission.class);
                    if (permission != null) {
                        methods.put(m.getName(), new PermissionMethod(m, permission));
                        System.out.println(m.getName());
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
