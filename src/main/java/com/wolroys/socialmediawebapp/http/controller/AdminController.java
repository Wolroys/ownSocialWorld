package com.wolroys.socialmediawebapp.http.controller;

import com.wolroys.socialmediawebapp.dto.UserReadDto;
import com.wolroys.socialmediawebapp.entity.User;
import com.wolroys.socialmediawebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping()
    public String findAll(Model model, @AuthenticationPrincipal UserDetails userDetails,
                          @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<UserReadDto> users = userService.findAll(pageable);
        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);
        return "user/adminPage";
    }
}
