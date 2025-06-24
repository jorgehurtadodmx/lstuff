package com.grupo1.controllers;

import com.grupo1.entities.Comment;
import com.grupo1.entities.Project;
import com.grupo1.entities.Task;
import com.grupo1.entities.User;
import com.grupo1.enums.Priority;
import com.grupo1.enums.TaskStatus;
import com.grupo1.repositories.CommentRepository;
import com.grupo1.repositories.ProjectRepository;
import com.grupo1.repositories.TaskRepository;
import com.grupo1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public String getTasks(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Project> userProjects = user.getProjects();

        List<Task> projectTasks = new ArrayList<>();
        List<Task> assignedTasks = new ArrayList<>();


        for (Project project : userProjects) {
            for (Task task : project.getTasks()) {
                projectTasks.add(task);
                if (task.getAssignedUser() != null && task.getAssignedUser().equals(user)) {
                    assignedTasks.add(task);
                }
            }
        }




        model.addAttribute("loggedUser", user);
        model.addAttribute("projectTasks", projectTasks);
        model.addAttribute("assignedTasks", assignedTasks);
        return "/task/task-list";
    }

    //TODO: buscador de tareas, de momento filtro por nombre
    //filtro por nomnre
    @GetMapping("/buscar")  //http://localhost:8080/tareas/buscar?title=tarea1
    public String findByName(Model model, @RequestParam("title") String title, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Project> userProjects = user.getProjects();
        List<Task> userTasks = new ArrayList<>();

        for (Project project : userProjects) {
            for (Task task : project.getTasks()) {
                if (task.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    userTasks.add(task);
                }
            }
        }

        //List<Task> tasks = taskRepository.findByTitleContainsIgnoreCase(title);
        model.addAttribute("tasks", userTasks);
        model.addAttribute("actualSearch", title);
        return "task/task-list";
    }



    @GetMapping("/project/{projectId}")
    public String getProjectTasks(@PathVariable Long projectId, Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));


        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isEmpty()) {
            model.addAttribute("error", "Proyecto no encontrado");
            return "task/task-list";
        }

        Project project = projectOpt.get();
            if (!project.getUsers().contains(user)) {
                model.addAttribute("error", "no tienes acceso a este proyecto");
                return "task/task-list";
            }

        List<Task> tasks = project.getTasks();

        model.addAttribute("loggedUser", user);
        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        return "task/task-list-by-project";







    }


    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id, Principal principal) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            model.addAttribute("task", task);

            List<Comment> comments = commentRepository.findByTaskOrderByCreationDateDesc(task);
            model.addAttribute("comments", comments);
            if (principal != null) {
                userRepository.findByUsername(principal.getName())
                        .ifPresent(user -> model.addAttribute("loggedUser", user));
            }
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
            model.addAttribute("priorities", Priority.values());
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
        LocalDate today = LocalDate.now();
        newTask.setCreatedAt(today);

       // Priority defaultPriority = Priority.MEDIA;
       // newTask.setPriority(defaultPriority);
        //newTask.setDueDate(calculateDate(today, defaultPriority));


        model.addAttribute("task", newTask);
        model.addAttribute("taskStatus", TaskStatus.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("projects", projectRepository.findAll());
        return "task/task-form";
    }

    private LocalDate calculateDate(LocalDate createdAt, Priority priority) {
        return switch (priority) {
            case CRITICA -> createdAt.plusDays(1);
            case MEDIA -> createdAt.plusDays(3);
            case BAJA -> createdAt.plusDays(7);
        };
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

        if (task.getDueDate() == null && task.getCreatedAt() != null) {
            task.setDueDate(calculateDate(task.getCreatedAt(), task.getPriority()));
        }

        task.setUpdatedAt(LocalDate.now());
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    //eliminar producto
    @PostMapping("/{id}/eliminar")
    public String delete(@PathVariable Long id) {

        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/assign")
    private String assignTask(@PathVariable Long id, Principal principal) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Tarea no encontrada"));
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado"));

        if (task.getAssignedUser() == null & task.getProject().getUsers().contains(user)) {
            task.setAssignedUser(user);
            taskRepository.save(task);
        }
            return "redirect:/tasks/" + id;
    }

}
