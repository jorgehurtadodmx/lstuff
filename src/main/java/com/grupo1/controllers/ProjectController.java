package com.grupo1.controllers;

import com.grupo1.entities.Project;
import com.grupo1.entities.User;
import com.grupo1.repositories.ProjectRepository;
import com.grupo1.repositories.TaskRepository;
import com.grupo1.repositories.UserRepository;
import com.grupo1.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getProjects(Model model, Principal principal) {
        List<Project> projects = projectRepository.findAll();


        String loggedUsername = principal.getName();

        // Acceder correctamente al usuario desde el Optional
        User user = userRepository.findByUsername(loggedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        model.addAttribute("loggedUser", user);
        model.addAttribute("projects", projects);
        return "/project/project-list";
    }

    // Ver detalles de un proyecto por ID
    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            model.addAttribute("project", project.get());
        } else {
            model.addAttribute("error", "Proyecto no encontrado");
        }
        return "project/project-detail";
    }

    // Formulario para crear nuevo proyecto
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("project", new Project());
        return "/project/project-form";
    }

    // Guardar proyecto (nuevo o editado)
    @PostMapping("/save")
    public String saveForm(@ModelAttribute Project project, Principal principal) {
        String username = principal.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            //asignar usuario que crea el proyecto, a dicho proyecto automaticamente.
            //pendiente asociar rango
            project.getUsers().add(user);
            user.getProjects().add(project);
            projectRepository.save(project);
            userRepository.save(user);
            return "redirect:/projects";
        } else {
            //si no esta autentificado, redirigir
            return "redirect:/login";
        }

    }

    // Formulario para editar proyecto existente
    @GetMapping("/{id}/editar")
    public String editar(Model model, @PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            model.addAttribute("project", project.get());
            return "/project/project-form";
        } else {
            model.addAttribute("error", "Proyecto no encontrado");
            return "redirect:/projects";
        }
    }

    // Eliminar proyecto
    @PostMapping("/{id}/eliminar")
    public String delete(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }
}
