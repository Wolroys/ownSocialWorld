package com.wolroys.socialmediawebapp.dto;

import lombok.Value;


public class UserCreateEditDto implements UserDto {
    private String username;
    private String email;
    private String password;

    public UserCreateEditDto() {
    }

    public UserCreateEditDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserCreateEditDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
