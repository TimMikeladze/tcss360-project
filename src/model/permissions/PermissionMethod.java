
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
     * <dt><b>Precondition:</b><dd> requires method != null
     * <dt><b>Postcondition:</b><dd> ensures A PermissionMethod is created
     * @param method the method
     * @param permission the permission
     */
    public PermissionMethod(final Method method, final Permission permission) {
        this.method = method;
        this.permission = permission;
    }
    
    /**
     * Gets the permission.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A permission is returned
     * @return the permission
     */
    public Permission getPermission() {
        return permission;
    }
    
    /**
     * Gets the method.
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A method is returned
     * @return the method
     */
    public Method getMethod() {
        return method;
    }
    
    /**
     * The PermissionMethods hashCode
     * 
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A hashcode is returned
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
     * <dt><b>Precondition:</b><dd> requires obj != null
     * <dt><b>Postcondition:</b><dd> ensures A boolean is returned
     * @return true if equal otherwise false
     */
    @Override
    public boolean equals(final Object obj) {
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
     * <dt><b>Precondition:</b><dd> none
     * <dt><b>Postcondition:</b><dd> ensures A tostring is returned
     * @return all relevant information about the PermissionMethod
     */
    @Override
    public String toString() {
        return method.getName() + ", " + permission;
    }
    
}
