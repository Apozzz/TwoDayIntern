package com.twoday.shop.shopmodule.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.shop.shopmodule.response.ApiResponse;
import com.twoday.shop.shopmodule.user.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final WebClient webClient;

    @Override
    public Mono<ResponseEntity<ApiResponse>> registerUser(UserDto userDto) {
        return webClient.post()
                .uri("/v1/users/register")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .map(response -> {
                    if (response.getStatus() == HttpStatus.CREATED.value()) {
                        return ResponseEntity.status(HttpStatus.CREATED).body(response);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                })
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null)));
    }

}
