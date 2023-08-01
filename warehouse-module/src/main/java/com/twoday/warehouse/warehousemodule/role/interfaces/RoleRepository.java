package com.twoday.warehouse.warehousemodule.role.interfaces;

import com.twoday.dto.dtomodule.RoleDto;
import com.twoday.warehouse.warehousemodule.interfaces.GenericRepository;
import com.twoday.warehouse.warehousemodule.role.Role;

public interface RoleRepository extends GenericRepository<Role, Long> {
    
    RoleDto save(RoleDto roleDto);
    Role findByName(String name);

}
