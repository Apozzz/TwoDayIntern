package com.twoday.dto.dtomodule;

import java.util.Set;

public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Set<RoleDto> roles;

    public UserDto(Long id, String username, String password, Set<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles + "]";
    }

}
