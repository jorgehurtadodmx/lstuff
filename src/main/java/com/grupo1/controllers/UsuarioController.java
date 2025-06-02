package com.grupo1.controllers;


import com.grupo1.entities.Usuario;
import com.grupo1.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.Optional;

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
        return "/usuario/user-list";
    }


    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());

        } else {
            model.addAttribute("error", "404 usuario no encontrado");
        }
        return "/usuario/user-detail";
    }

    @GetMapping("/{id}/editar")
        public String editar(Model model, @PathVariable Long id)  {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            //resto de datos relacionados con usuario para pasarselos a la vista.
            //model.addAttribute("proyectos", proyectoRepository.findAll());
        } else {
            model.addAttribute("error", "usuario no encontrado");
        }
        return "/usuario/user-detail-edit";
    }

}
