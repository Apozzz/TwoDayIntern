package com.twoday.warehouse.warehousemodule.user.interfaces;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.warehouse.warehousemodule.interfaces.GenericConverter;
import com.twoday.warehouse.warehousemodule.user.User;

public interface UserConverter extends GenericConverter<User, UserDto> {
    
    UserDto toDto(User user);
    User fromDto(UserDto userDto);

}
