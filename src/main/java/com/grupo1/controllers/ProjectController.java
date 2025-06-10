package com.grupo1.controllers;

import com.grupo1.entities.Project;
import com.grupo1.repositories.ProjectRepository;
import com.grupo1.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public ProjectController(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public String getProjects(Model model) {
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "/project/project-list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("project", new Project());
        return "project/project-form";
    }
}
