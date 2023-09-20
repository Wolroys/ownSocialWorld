package com.wolroys.socialmediawebapp.http.controller;

import com.wolroys.socialmediawebapp.dto.PostDto;
import com.wolroys.socialmediawebapp.entity.Comment;
import com.wolroys.socialmediawebapp.entity.Post;
import com.wolroys.socialmediawebapp.mapper.PostMapper;
import com.wolroys.socialmediawebapp.service.CommentService;
import com.wolroys.socialmediawebapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final PostMapper mapper;

    @GetMapping
    public String showAll(Model model){
        List<PostDto> posts = postService.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("newPost", new PostDto());
        return "home/homePage";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable int id, Model model){
            return postService.findById(id)
                    .map(post -> {
                        List<Comment> comments = commentService.findAllByPostId(id);
                        model.addAttribute("comments", comments);
                        model.addAttribute("post", post);
                        model.addAttribute("comment", new Comment());
                        return "home/post";
                    })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @GetMapping("/create")
//    public String produce(Model model){
//        model.addAttribute("post", new PostDto());
//        return "";
//    }

    @PostMapping("/create")
    public String create(@ModelAttribute PostDto post, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("registrationSuccess", true);
        postService.create(post);
        return "redirect:/home";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model){
        Optional<PostDto> existPost = postService.findById(id);

        Post post = mapper.toEntity(existPost.get());

        model.addAttribute("post", existPost.get());

        return "home/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute PostDto post){
        return postService.edit(id, post)
                .map(it -> "redirect:/home/{id}")
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id){
        postService.delete(id);
        return "redirect:/home";
    }

}
