package com.twoday.warehouse.warehousemodule.unit.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.warehouse.warehousemodule.user.User;
import com.twoday.warehouse.warehousemodule.user.UserServiceImpl;
import com.twoday.warehouse.warehousemodule.user.exceptions.UserAlreadyExistsException;
import com.twoday.warehouse.warehousemodule.user.interfaces.UserConverter;
import com.twoday.warehouse.warehousemodule.user.interfaces.UserRepository;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterThrowsUserAlreadyExistsException() {
        UserDto userDto = new UserDto();
        userDto.setUsername("existingUser");
        User user = new User();
        user.setUsername("existingUser");
        when(userConverter.fromDto(userDto)).thenReturn(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(userDto));
    }

    @Test
    public void testRegisterSavesUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("newUser");
        userDto.setPassword("rawPassword");
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("rawPassword");
        when(userConverter.fromDto(userDto)).thenReturn(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        userDto.setPassword("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(userConverter.toDto(user)).thenReturn(userDto);
        UserDto result = userService.register(userDto);
        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    public void testLoadByUsernameThrowsUsernameNotFoundException() {
        String username = "unknownUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }

    @Test
    public void testLoadByUsernameReturnsUserDetails() {
        String username = "knownUser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("somePassword");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        org.springframework.security.core.userdetails.User result = 
            (org.springframework.security.core.userdetails.User) userService.loadUserByUsername(username);
        assertNotNull(result);
        assertEquals("knownUser", result.getUsername());
        assertEquals("somePassword", result.getPassword());
        assertTrue(result.getAuthorities().isEmpty());
    }
    
}
