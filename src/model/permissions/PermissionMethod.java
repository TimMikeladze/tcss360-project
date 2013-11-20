
package model.permissions;

import java.lang.reflect.Method;

/**
 * Permissions interactions class.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
public class PermissionMethod {
    
    /**
     * The method.
     */
    private Method method;
    
    /**
     * The permission.
     */
    private Permission permission;
    
    /**
     * Creates a new permission method with a method and a permission.
     * 
     * @param method the method
     * @param permission the permission
     */
    public PermissionMethod(Method method, Permission permission) {
        this.method = method;
        this.permission = permission;
    }
    
    /**
     * Gets the permission.
     * 
     * @return the permission
     */
    public Permission getPermission() {
        return permission;
    }
    
    /**
     * Gets the method.
     * 
     * @return the method
     */
    public Method getMethod() {
        return method;
    }
    
    /**
     * The PermissionMethods hashCode
     * 
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        return result;
    }
    
    /**
     * Checks two PermissionMethods to see if they are equal
     * 
     * @return true if equal otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PermissionMethod other = (PermissionMethod) obj;
        if (method == null) {
            if (other.method != null)
                return false;
        }
        else if (!method.getName().equals(other.method.getName()))
            return false;
        return true;
    }
    
    /**
     * The PermissionMethods toString
     * 
     * @return all relevant information about the PermissionMethod
     */
    public String toString() {
        return method.getName() + ", " + permission;
    }
    
}
