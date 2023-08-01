package com.twoday.warehouse.warehousemodule.user.interfaces;

import java.util.Optional;

import com.twoday.warehouse.warehousemodule.interfaces.GenericRepository;
import com.twoday.warehouse.warehousemodule.user.User;

public interface UserRepository extends GenericRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

}
