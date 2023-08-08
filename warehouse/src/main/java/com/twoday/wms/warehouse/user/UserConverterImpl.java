package com.twoday.wms.warehouse.user;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.twoday.wms.dto.UserDto;
import com.twoday.wms.warehouse.user.interfaces.UserConverter;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserConverterImpl implements UserConverter {

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Override
    public User fromDto(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }
    
}
