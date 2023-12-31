package com.wolroys.socialmediawebapp.dto;

import com.wolroys.socialmediawebapp.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private int id;

    @NotEmpty(message = "You should specify the title")
    @Size(max = 35, message = "The title exceeds 35 characters")
    private String title;

    @NotBlank(message = "You should write something")
    private String content;

    private Long likes;

    private User user;

    public PostDto() {
    }

    public PostDto(int id, String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }


}
