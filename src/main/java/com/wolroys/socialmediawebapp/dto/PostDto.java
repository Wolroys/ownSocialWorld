package com.wolroys.socialmediawebapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class PostDto {

    private int id;

    @NotEmpty(message = "You should specify the title")
    private String title;

    @NotBlank(message = "You should write something")
    private String content;

    public PostDto() {
    }

    public PostDto(int id, String title, String content) {
        this.title = title;
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
