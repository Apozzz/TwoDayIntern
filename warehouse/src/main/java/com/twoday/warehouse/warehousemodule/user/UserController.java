package com.twoday.warehouse.warehousemodule.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.warehouse.warehousemodule.user.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto registeredUser = userService.register(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

}
