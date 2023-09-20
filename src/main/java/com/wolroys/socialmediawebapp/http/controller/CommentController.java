package com.wolroys.socialmediawebapp.http.controller;

import com.wolroys.socialmediawebapp.entity.Comment;
import com.wolroys.socialmediawebapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comment")
    public String createComment(@PathVariable int postId, @ModelAttribute Comment comment){
        commentService.create(postId, comment);
        return "redirect:/home/" + postId;
    }

}
