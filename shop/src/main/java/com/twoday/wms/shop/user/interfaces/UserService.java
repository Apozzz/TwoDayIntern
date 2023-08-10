package com.twoday.wms.shop.user.interfaces;

import org.springframework.http.ResponseEntity;

import com.twoday.wms.dto.UserDto;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<ResponseEntity<UserDto>> registerUser(UserDto userDto);

}