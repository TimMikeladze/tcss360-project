package model.permissions;

public enum PermissionLevel {
	REVIEWER(200), AUTHOR(100), SUBPROGRAM_CHAR(300), PROGRAM_CHAIR(400), ADMIN(
			500);

	private final int permission;

	private PermissionLevel(int permission) {
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

}
