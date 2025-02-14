package com.twoday.wms.warehouse.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.twoday.wms.warehouse.user.User;
import com.twoday.wms.warehouse.user.interfaces.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitializerConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!userRepository.findByUsername("ra").isPresent()) { 
            User user = new User();
            user.setUsername("ra");
            user.setPassword(passwordEncoder.encode("ra"));
            userRepository.save(user);
        }
    }
}