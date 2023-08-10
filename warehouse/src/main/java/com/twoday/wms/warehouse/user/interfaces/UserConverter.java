package com.twoday.wms.warehouse.user.interfaces;

import com.twoday.wms.dto.UserDto;
import com.twoday.wms.warehouse.interfaces.GenericConverter;
import com.twoday.wms.warehouse.user.User;

public interface UserConverter extends GenericConverter<User, UserDto> {
    
    com.twoday.wms.dto.UserDto toDto(User user);
    User fromDto(UserDto userDto);

}
