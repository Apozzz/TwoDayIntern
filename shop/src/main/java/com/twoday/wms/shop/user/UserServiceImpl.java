package com.twoday.wms.shop.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.twoday.wms.dto.UserDto;
import com.twoday.wms.shop.user.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final WebClient webClient;

    @Override
    public Mono<ResponseEntity<UserDto>> registerUser(UserDto userDto) {
        return webClient.post()
                .uri("/v1/users/register")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(UserDto.class)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
