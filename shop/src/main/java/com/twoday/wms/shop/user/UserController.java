package com.twoday.wms.shop.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.wms.dto.UserDto;
import com.twoday.wms.shop.logging.annotations.LogMessage;
import com.twoday.wms.shop.user.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    static final String BEFORE_REGISTER_LOG = "Attempting to register user with details: {}";
    static final String AFTER_REGISTER_LOG = "User registration was successful.";

    private final UserService userService;

    @PostMapping("/register")
    @LogMessage(before = BEFORE_REGISTER_LOG, after = AFTER_REGISTER_LOG, loggerClass = UserController.class)
    public Mono<ResponseEntity<UserDto>> register(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

}
