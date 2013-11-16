package model.permissions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import model.util.PackageReflector;

/**
 * The Permissions enum
 */
public enum Permissions {

	REVIEWER(100), AUTHOR(200), SUBPROGRAM_CHAR(300), PROGRAM_CHAIR(400), ADMIN(
			500);

	private static final HashMap<Class<?>, HashMap<String, PermissionMethod>> classMap = loadClassMap();

	private final int permission;

	private Permissions(int permission) {
		this.permission = permission;
	}

	/**
	 * Gets the permission.
	 * 
	 * @return the permission
	 */
	public int getPermission() {
		return permission;
	}

	/**
	 * Does a non strict permission check
	 * 
	 * @param className
	 *            the class where the method is
	 * @param method
	 *            method trying to access
	 * @param permission
	 *            the permission required to access method
	 * @return returns true if can access
	 */
	public static boolean hasPermission(Class<?> className, String method,
			Permissions permission) {
		return hasPermission(className, method, permission, false);
	}

	/**
	 * Does a permission check
	 * 
	 * @param className
	 *            the class where the method is
	 * @param method
	 *            method trying to access
	 * @param permission
	 *            the permission required to access method
	 * @param strict
	 *            if true the permission level given must match the permission
	 *            level of the method
	 * @return returns true if can access
	 */
	public static boolean hasPermission(Class<?> className, String method,
			Permissions permission, boolean strict) {
		boolean hasPermission = false;
		int givenPermission = permission.getPermission();

		HashMap<String, PermissionMethod> methods = classMap.get(className);
		if (methods != null && methods.containsKey(method)) {
			int methodPermission = methods.get(method).getPermission().level();
			if ((strict && givenPermission == methodPermission)
					|| (!strict && givenPermission >= methodPermission)) {
				hasPermission = true;
			}
		}
		return hasPermission;
	}

	/**
	 * Loads all a map of classes and methods which contain the the Permission
	 * annotation
	 * 
	 * @return
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
						methods.put(m.getName(), new PermissionMethod(m,
								permission));
					}
				}
				map.put(c, methods);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

}
