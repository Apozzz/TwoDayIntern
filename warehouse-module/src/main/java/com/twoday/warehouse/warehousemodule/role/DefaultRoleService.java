package com.twoday.warehouse.warehousemodule.role;

import org.springframework.stereotype.Service;

import com.twoday.dto.dtomodule.RoleDto;
import com.twoday.warehouse.warehousemodule.role.interfaces.RoleConverter;
import com.twoday.warehouse.warehousemodule.role.interfaces.RoleRepository;
import com.twoday.warehouse.warehousemodule.role.interfaces.RoleService;

@Service
public class DefaultRoleService implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public DefaultRoleService(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = roleRepository.save(roleConverter.fromDto(roleDto));
        return roleConverter.toDto(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
    
}
