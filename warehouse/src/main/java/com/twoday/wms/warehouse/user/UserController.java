package com.twoday.wms.warehouse.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.warehouse.user.interfaces.UserService;
import com.twoday.wms.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        log.info("Attempting to register user with username: {}", userDto.getUsername());
        UserDto registeredUser = userService.register(userDto);
        log.info("Successfully registered user with username: {}", registeredUser.getUsername());
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

}
