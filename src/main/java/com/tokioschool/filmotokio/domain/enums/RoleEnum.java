package com.tokioschool.filmotokio.domain.enums;

public enum RoleEnum {

    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private String roleDesc;

    RoleEnum(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleDesc() {
        return roleDesc;
    }
}
