package com.wolroys.socialmediawebapp.http.controller;

import com.wolroys.socialmediawebapp.dto.UserCreateEditDto;
import com.wolroys.socialmediawebapp.dto.UserReadDto;
import com.wolroys.socialmediawebapp.entity.User;
import com.wolroys.socialmediawebapp.mapper.UserMapper;
import com.wolroys.socialmediawebapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public String findAll(Model model, @AuthenticationPrincipal UserDetails userDetails,
                          @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<UserReadDto> users = userService.findAll(pageable);
        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);
        return "user/adminPage";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable int id, @AuthenticationPrincipal UserDetails userDetails){
        return userService.findById(id)
                .map(user -> {
                    User currentUser = userService.findByUsername(userDetails.getUsername());
                    boolean hasSubscribe = userService.hasSubscribe(currentUser, user);
                    long subscribes = userService.countSubscribes(user);
                    System.out.println(hasSubscribe);
                    model.addAttribute("user", user);
                    model.addAttribute("hasSubscribe", hasSubscribe);
                    model.addAttribute("subscribes", subscribes);
                    model.addAttribute("currentUser", currentUser);
                    return "user/user";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String create(Model model){
        model.addAttribute("user", new UserCreateEditDto());
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registry(@ModelAttribute @Validated UserCreateEditDto user,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }

        redirectAttributes.addFlashAttribute("registrationSuccess", true);
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model){
         Optional<User> user = userService.findById(id);

         model.addAttribute("user", user);
         return "user/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute @Valid UserCreateEditDto user,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/{id}/edit";
        }

        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id){
        userService.remove(id);
        return "redirect:/users";
    }

    @GetMapping("/subscribe/{id}")
    public String subscribe(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails){
        User currentUser = userService.findByUsername(userDetails.getUsername());
        User user = (userService.findById(id).get());
        userService.subscribe(currentUser, user);

        return "redirect:/users/" + id;
    }

    @GetMapping("/unsubscribe/{id}")
    public String unsubscribe(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails){
        User currentUser = userService.findByUsername(userDetails.getUsername());
        User user = (userService.findById(id).get());
        userService.unsubscribe(currentUser, user);

        return "redirect:/users/" + id;
    }

}
