package com.twoday.shop.shopmodule.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.shop.shopmodule.response.ApiResponse;
import com.twoday.shop.shopmodule.user.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Mono<ResponseEntity<ApiResponse>> register(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

}
