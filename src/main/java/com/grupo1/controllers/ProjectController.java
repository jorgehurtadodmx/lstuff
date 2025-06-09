package com.grupo1.controllers;

import com.grupo1.entities.Project;
import com.grupo1.entities.Task;
import com.grupo1.repositories.ProjectRepository;
import com.grupo1.repositories.TaskRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public String getProjects(Model model) {
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "/projects/projects-list";
    }

    @GetMapping("/projects/buscar")
    public String findByName(Model model, @RequestParam("title") String title) {
        List<Project> projects = projectRepository.findByNombreContainsIgnoreCase(title);
        model.addAttribute("projects", projects);
        return "project/project-list";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            model.addAttribute("project", project.get());
        } else {
            model.addAttribute("error", "proyecto no encontrada");
        }
        return "/project/project-detail";
    }

    @GetMapping("/{id}/editar")
    public String editar(Model model, @PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            model.addAttribute("project", project.get());
            model.addAttribute("projects", projectRepository.findAll());
            return "project/project-form";

        } else {
            model.addAttribute("error", "tarea no encontrada");
        }
        return "redirect:/projects";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("projects", new Project());
        model.addAttribute("projects", projectRepository.findAll());
        return "project/project-form";
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute Project project) {
        projectRepository.save(project);
        return "redirect:/projects";
    }

    @PostMapping("/{id}/eliminar")
    public String delete(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }
}
