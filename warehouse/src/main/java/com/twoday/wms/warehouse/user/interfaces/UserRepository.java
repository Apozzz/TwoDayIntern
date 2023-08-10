package com.twoday.wms.warehouse.user.interfaces;

import java.util.Optional;

import com.twoday.wms.warehouse.interfaces.GenericRepository;
import com.twoday.wms.warehouse.user.User;

public interface UserRepository extends GenericRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

}
