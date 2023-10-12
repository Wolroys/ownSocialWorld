package com.wolroys.socialmediawebapp.http.controller;

import com.wolroys.socialmediawebapp.dto.PostDto;
import com.wolroys.socialmediawebapp.entity.Comment;
import com.wolroys.socialmediawebapp.entity.Post;
import com.wolroys.socialmediawebapp.entity.User;
import com.wolroys.socialmediawebapp.mapper.PostMapper;
import com.wolroys.socialmediawebapp.service.CommentService;
import com.wolroys.socialmediawebapp.service.LikeService;
import com.wolroys.socialmediawebapp.service.PostService;
import com.wolroys.socialmediawebapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;
    private final LikeService likeService;
    private final PostMapper mapper;

    @GetMapping
    public String showAll(Model model, @AuthenticationPrincipal UserDetails userDetails,
                          @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        Page<PostDto> posts = postService.findAll(pageable);
        User currentUser = userService.findByUsername(userDetails.getUsername());
        for (PostDto postDto : posts){
            likeService.getCount(postDto);
        }
        model.addAttribute("posts", posts);
        model.addAttribute("newPost", new PostDto());
        model.addAttribute("currentUser", currentUser);
        return "home/homePage";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable int id, Model model,
                           @AuthenticationPrincipal UserDetails userDetails){
            return postService.findById(id)
                    .map(post -> {
                        likeService.getCount(post);
                        User user = userService.findByUsername(userDetails.getUsername());
                        boolean currentUserLiked = likeService.isLiked(user.getId(), post.getId());
                        List<Comment> comments = commentService.findAllByPostId(id);
                        model.addAttribute("comments", comments);
                        model.addAttribute("post", post);
                        model.addAttribute("comment", new Comment());
                        model.addAttribute("isLiked", currentUserLiked);
                        return "home/post";
                    })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/create")
    public String create(@ModelAttribute @Valid PostDto post, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/home";
        }

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
    public String update(@PathVariable int id, @ModelAttribute @Valid PostDto post,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("post", post);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/home/{id}/edit";
        }

        return postService.edit(id, post)
                .map(it -> "redirect:/home/{id}")
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id){
        postService.delete(id);
        return "redirect:/home";
    }

    @GetMapping("/{postId}/like")
    public String like(@PathVariable int postId){
        likeService.like(postId);

        return "redirect:/home";
    }

    @GetMapping("/{postId}/likePost")
    public String localLike(@PathVariable int postId){
        likeService.like(postId);

        return "redirect:/home/{postId}";
    }

    @GetMapping("/{postId}/{userId}/like")
    public String userLike(@PathVariable int postId, @PathVariable int userId){
        likeService.like(postId);

        return "redirect:/users/{userId}";
    }
}
