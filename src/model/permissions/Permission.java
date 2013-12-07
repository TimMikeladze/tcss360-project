
package model.permissions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permissions interface used for annotations.
 * 
 * @author Tim Mikeladze
 * @version 11-16-2013
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    
    /**
     * Sets the permission level required to access a method
     * 
     * @return the permission level
     */
    int level() default 0;
    
    /**
     * Sets whether the given permission level must be equal to the method's permission level or
     * be equal and greater than
     * 
     * @return true, if successful
     */
    boolean strict() default false;
}