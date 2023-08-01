package com.twoday.warehouse.warehousemodule.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twoday.dto.dtomodule.UserDto;
import com.twoday.warehouse.warehousemodule.role.Role;
import com.twoday.warehouse.warehousemodule.role.interfaces.RoleService;
import com.twoday.warehouse.warehousemodule.user.exceptions.UserAlreadyExistsException;
import com.twoday.warehouse.warehousemodule.user.interfaces.UserConverter;
import com.twoday.warehouse.warehousemodule.user.interfaces.UserRepository;
import com.twoday.warehouse.warehousemodule.user.interfaces.UserService;

@Service
public class DefaultUserService implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserConverter userConverter;

    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userConverter = userConverter;
    }

    @Override
    public void register(UserDto userDto) {
        User user = userConverter.fromDto(userDto);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username %s already exists".formatted(user.getUsername()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(getRolesSet(user));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: %s".formatted(username)));
    }

    private Set<Role> getRolesSet(User user) {
        Set<Role> roles = new HashSet<>();

        for (Role role : user.getRoles()) {
            Role existingRole = roleService.findByName(role.getAuthority());
            roles.add(existingRole);
        }

        return roles;
    }
    
}
