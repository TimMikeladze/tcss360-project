package model.permissions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permissions interface.
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
     * Gets the permission level.
     * 
     * @return the permission level
     */
	int level() default 0;
}