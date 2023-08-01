package com.twoday.warehouse.warehousemodule.role;

import org.springframework.stereotype.Component;

import com.twoday.dto.dtomodule.RoleDto;
import com.twoday.warehouse.warehousemodule.role.interfaces.RoleConverter;

@Component
public class DefaultRoleConverter implements RoleConverter {

    @Override
    public RoleDto toDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getAuthority());

        return roleDto;
    }

    @Override
    public Role fromDto(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        roleDto.setName(roleDto.getName());

        return role;
    }

}
