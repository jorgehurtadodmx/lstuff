package com.grupo1.controllers;


import com.grupo1.entities.Usuario;
import com.grupo1.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @GetMapping
    public String getUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        model.addAttribute("usuarios", usuarios);
        return "user-list";
    }
}
