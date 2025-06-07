package com.grupo1.controllers;

import com.grupo1.entities.User;
import com.grupo1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    // Mostrar formulario de login
    @GetMapping("/login")
    public String login() {
        return "auth/login";  // apunta a templates/auth/login.html
    }

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";  // apunta a templates/auth/register.html
    }

    // Procesar formulario de registro
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model) {
        try {
            userService.register(user, confirmPassword);
            return "redirect:/auth/login?success";  // redirige a login con parámetro para mostrar mensaje
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";  // vuelve a mostrar registro con mensaje de error
        }
    }
}
