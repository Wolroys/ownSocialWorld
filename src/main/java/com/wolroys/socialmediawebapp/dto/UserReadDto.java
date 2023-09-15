package com.wolroys.socialmediawebapp.dto;

import com.wolroys.socialmediawebapp.entity.Role;
import lombok.Value;


public class UserReadDto implements UserDto{
    private int id;
    private String username;
    private String email;
    private Role role;

    public UserReadDto() {
    }

    public UserReadDto(int id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
