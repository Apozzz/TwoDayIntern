package com.twoday.shop.shopmodule.unit.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.shop.shopmodule.user.UserController;
import com.twoday.shop.shopmodule.user.interfaces.UserService;

import reactor.core.publisher.Mono;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(userController).build();
    }

    @Test
    public void testRegister() {
        UserDto mockUser = new UserDto(1L, "Username", "Password");
        when(userService.registerUser(Mockito.any(UserDto.class))).thenReturn(Mono.just(ResponseEntity.ok(mockUser)));

        webTestClient.post()
                .uri("/v1/users/register")
                .bodyValue(mockUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(actualUser -> {
                    assertEquals(mockUser.getId(), actualUser.getId());
                    assertEquals(mockUser.getUsername(), actualUser.getUsername());
                    assertEquals(mockUser.getPassword(), actualUser.getPassword());
                });

        verify(userService, times(1)).registerUser(Mockito.any(UserDto.class));
    }

}