package com.twoday.warehouse.warehousemodule.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder auth;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder,
            AuthenticationManagerBuilder auth) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.auth = auth;
        configureGlobal();
    }

    private void configureGlobal() {
        try {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/v1/users/register").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
