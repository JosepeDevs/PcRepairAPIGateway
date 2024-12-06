package com.josepedevs.domain.dto.valueobjects;

import com.josepedevs.domain.exceptions.RoleNotValidException;
import lombok.Getter;

@Getter
public enum RoleVo {
 	USER("Read"),
    EDITOR("Read, Write"),
    ADMIN("Read, Write, Delete");

    private final String permissions;

    RoleVo(String permissions) {

        if(permissions.isBlank()) {
            throw new RoleNotValidException("Role not valid", permissions);
        }

        this.permissions = permissions;
    }

    public String getRoleName(RoleVo role) {
        return role.name();
    }
}
