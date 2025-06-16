package com.grupo1.controllers;

import com.grupo1.entities.Project;
import com.grupo1.entities.Task;
import com.grupo1.enums.TaskStatus;
import com.grupo1.repositories.ProjectRepository;
import com.grupo1.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public String getTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "/task/task-list";
    }

    //TODO: buscador de tareas, de momento filtro por nombre
    //filtro por nomnre
    @GetMapping("/buscar")  //http://localhost:8080/tareas/buscar?title=tarea1
    public String findByName(Model model, @RequestParam("title") String title) {
        List<Task> tasks = taskRepository.findByTitleContainsIgnoreCase(title);
        model.addAttribute("tasks", tasks);
        model.addAttribute("actualSearch", title);
        return "task/task-list";
    }

    //DETALE por ID de task
    //posiblemente convenga que la información de la propia tarea aparezca en la misma VISTA.
    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
        } else {
            model.addAttribute("error", "tarea no encontrada");
        }
        return "/task/task-detail";
    }

    //permite editar tarea existente
    @GetMapping("/{id}/editar")
    public String editar(Model model, @PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            model.addAttribute("taskStatus", TaskStatus.values());
            model.addAttribute("projects", projectRepository.findAll());
            return "task/task-form";

        } else {
            model.addAttribute("error", "tarea no encontrada");
        }
        return "redirect:/tasks";
    }

    //creacion de tareas
    @GetMapping("/new")
    public String createTaskFromProject(@RequestParam(name = "projectId", required = false) Long projectId, Model model) {
        Task newTask = new Task();
        if (projectId != null) {
            Optional<Project> project = projectRepository.findById(projectId);
          project.ifPresent(newTask::setProject);
        }
        model.addAttribute("task", newTask);
        model.addAttribute("taskStatus", TaskStatus.values());
        model.addAttribute("projects", projectRepository.findAll());
        return "task/task-form";
    }


    @PostMapping("/save")
    public String saveForm(@ModelAttribute Task task) {
        Long projectId =  (task.getProject() != null) ? task.getProject().getId() : null;
        if (projectId != null) {
            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (optionalProject.isEmpty()) {
                throw new IllegalArgumentException("Proyecto no encontrado");
            }
            task.setProject(optionalProject.get());
        }
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    //eliminar producto
    @PostMapping("/{id}/eliminar")
    public String delete(@PathVariable Long id) {

        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }

}
