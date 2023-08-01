package com.twoday.warehouse.warehousemodule.role.interfaces;

import com.twoday.dto.dtomodule.RoleDto;
import com.twoday.warehouse.warehousemodule.interfaces.GenericConverter;
import com.twoday.warehouse.warehousemodule.role.Role;

public interface RoleConverter extends GenericConverter<Role, Long> {
    
    RoleDto toDto(Role role);
    Role fromDto(RoleDto roleDto);

}
