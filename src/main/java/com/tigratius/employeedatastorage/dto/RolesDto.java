package com.tigratius.employeedatastorage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigratius.employeedatastorage.model.Role;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolesDto {

    private Long id;
    private String name;

    public Role toRole()
    {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }

    public static RolesDto fromRole(Role role) {
        RolesDto rolesDto = new RolesDto();
        rolesDto.setId(role.getId());
        rolesDto.setName(role.getName());
        return rolesDto;
    }
}

