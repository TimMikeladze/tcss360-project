package model.permissions;

public enum Permissions {
    REVIEWER(100),
    AUTHOR(200),
    SUBPROGRAM_CHAR(300),
    PROGRAM_CHAIR(400),
    ADMIN(500);
    
    int permission;
    
    private Permissions(int permission) {
        this.permission = permission;
    } 
}
