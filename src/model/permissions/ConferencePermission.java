package model.permissions;


public class ConferencePermission {
    private int conferenceID;
    private int userID;
    private int permissionID;

    public int getConferenceID() {
        return conferenceID;
    }

    public int getUserID() {
        return userID;
    }

    public int getPermissionID() {
        return permissionID;
    }

    public String getRole() {
        String role = "";
        for (PermissionLevel p : PermissionLevel.values()) {
            if (p.getPermission() == permissionID) {
                role = PermissionLevel.getRole(p);
                break;
            }
        }
        return role;
    }
}
