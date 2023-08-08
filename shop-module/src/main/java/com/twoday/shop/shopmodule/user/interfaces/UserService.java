package com.twoday.shop.shopmodule.user.interfaces;

import org.springframework.http.ResponseEntity;

import com.twoday.dto.dtomodule.UserDto;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<ResponseEntity<UserDto>> registerUser(UserDto userDto);

}