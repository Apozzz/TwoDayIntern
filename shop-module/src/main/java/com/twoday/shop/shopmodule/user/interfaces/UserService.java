package com.twoday.shop.shopmodule.user.interfaces;

import org.springframework.http.ResponseEntity;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.shop.shopmodule.response.ApiResponse;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<ResponseEntity<ApiResponse>> registerUser(UserDto userDto);

}