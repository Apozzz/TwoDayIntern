package com.twoday.wms.warehouse.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoday.wms.warehouse.user.interfaces.UserRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    
}
