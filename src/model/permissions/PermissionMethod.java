package model.permissions;

import java.lang.reflect.Method;

public class PermissionMethod {
	private Method method;
	private Permission permission;

	public PermissionMethod(Method method, Permission permission) {
		this.method = method;
		this.permission = permission;
	}

	public Permission getPermission() {
		return permission;
	}

	public Method getMethod() {
		return method;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		return result;
	}

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
		} else if (!method.getName().equals(other.method.getName()))
			return false;
		return true;
	}

	public String toString() {
		return method.getName() + ", " + permission;
	}

}
