package com.wolroys.socialmediawebapp.dto;


import jakarta.validation.constraints.*;

public class UserCreateEditDto implements UserDto {
    @NotEmpty(message = "You need to fill username field")
    private String username;

    @Email(message = "This email is incorrect")
    @NotBlank(message = "You need to fill email field")
    private String email;

    @NotEmpty(message = "You must to fill password field")
    @Size(min = 8, message = "Your password must consist of at least 8 characters")
    private String password;

    public UserCreateEditDto() {
    }

    public UserCreateEditDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
