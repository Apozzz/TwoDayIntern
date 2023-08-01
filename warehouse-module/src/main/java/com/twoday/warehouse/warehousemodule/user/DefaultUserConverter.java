package com.twoday.warehouse.warehousemodule.user;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.warehouse.warehousemodule.role.interfaces.RoleConverter;
import com.twoday.warehouse.warehousemodule.user.interfaces.UserConverter;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DefaultUserConverter implements UserConverter {

    private final RoleConverter roleConverter;

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles().stream().map(roleConverter::toDto).collect(Collectors.toSet()));

        return userDto;
    }

    @Override
    public User fromDto(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles().stream().map(roleConverter::fromDto).collect(Collectors.toSet()));

        return user;
    }
    
}
