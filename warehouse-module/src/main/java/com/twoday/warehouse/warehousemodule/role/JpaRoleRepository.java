package com.twoday.warehouse.warehousemodule.role;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.warehouse.warehousemodule.role.interfaces.RoleRepository;

public interface JpaRoleRepository extends JpaRepository<Role, Long>, RoleRepository {
}

