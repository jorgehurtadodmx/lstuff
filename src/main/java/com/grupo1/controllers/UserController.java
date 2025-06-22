package com.grupo1.controllers;

import com.grupo1.entities.User;
import com.grupo1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class UserController {
    @Autowired
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String perfilUsuario(Model model, Principal principal) {
        String loggedUsername = principal.getName();
        User user = userRepository.findByUsername(loggedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("loggedUser", user);
        return "user/profile";
    }
}