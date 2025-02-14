package com.twoday.wms.warehouse.user;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twoday.wms.warehouse.user.exceptions.UserAlreadyExistsException;
import com.twoday.wms.warehouse.user.interfaces.UserConverter;
import com.twoday.wms.warehouse.user.interfaces.UserRepository;
import com.twoday.wms.warehouse.user.interfaces.UserService;
import com.twoday.wms.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    @Override
    public UserDto register(UserDto userDto) {
        User user = userConverter.fromDto(userDto);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username %s already exists".formatted(user.getUsername()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userConverter.toDto(userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

}
