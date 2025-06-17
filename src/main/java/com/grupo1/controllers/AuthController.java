package com.grupo1.controllers;

import com.grupo1.entities.User;
import com.grupo1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    //Siempre añade 'user' al modelo para los formularios
    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    // Controlador para raíz "/"
    @GetMapping("/")
    public String home(Model model) {
        // No modales abiertos por defecto
        return "index";
    }

    // Mostrar formulario de login
    @GetMapping("/auth/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "success", required = false) String success,
                        Model model) {
        if (error != null) model.addAttribute("loginError", true);
        if (success != null) model.addAttribute("registeredSuccess", true);
        model.addAttribute("user", new User()); // necesario para el modal registro
        return "index";
    }


   /* // Mostrar formulario de registro
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";  // apunta a templates/auth/register.html
    }*/

    // Procesar formulario de registro
    @PostMapping("/auth/register")
    public String registerUser(@ModelAttribute User user,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model) {
        try {
            userService.register(user, confirmPassword);
            return "redirect:/auth/login?success";  // sigue igual para login OK
        } catch (RuntimeException e) {
            model.addAttribute("user", user);                 // importante para mantener datos
            model.addAttribute("error", e.getMessage());      // el mensaje de error
            model.addAttribute("showRegisterModal", true);    //para que se reabra el modal
            return "index";  //  renderiza index con el modal visible
        }
    }
}