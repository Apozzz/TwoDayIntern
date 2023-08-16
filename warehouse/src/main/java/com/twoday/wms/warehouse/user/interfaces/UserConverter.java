package com.twoday.wms.warehouse.user.interfaces;

import com.twoday.wms.warehouse.interfaces.GenericConverter;
import com.twoday.wms.warehouse.user.User;
import com.twoday.wms.dto.UserDto;

public interface UserConverter extends GenericConverter<User, UserDto> {
    
    UserDto toDto(User user);
    User fromDto(UserDto userDto);

}
