package com.twoday.warehouse.warehousemodule.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.warehouse.warehousemodule.user.interfaces.UserRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
