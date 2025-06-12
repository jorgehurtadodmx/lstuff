package com.grupo1.controllers;

import com.grupo1.entities.Project;
import com.grupo1.repositories.ProjectRepository;
import com.grupo1.repositories.TaskRepository;
import com.grupo1.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectController(ProjectRepository projectRepository,
            TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/detail")
    public String detailUser() {
        return "/project/project-detail";
    }

    @GetMapping("/new")
    public String projectForm(Model model) {
        model.addAttribute("project", new Project());
        return "/project/project-form";
    }

    @GetMapping
    public String getProjects(Model model) {
        List<Project> project = projectRepository.findAll();
        return "/project/project-list";
    }
}
