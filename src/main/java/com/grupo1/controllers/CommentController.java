package com.grupo1.controllers;

import com.grupo1.entities.Comment;
import com.grupo1.entities.Task;
import com.grupo1.entities.User;
import com.grupo1.repositories.CommentRepository;
import com.grupo1.repositories.TaskRepository;
import com.grupo1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/comentarios")
public class CommentController {

        @Autowired
        private CommentRepository comentarioRepository;

        @Autowired
        private TaskRepository taskRepository;

        @Autowired
        private UserRepository userRepository;

        @GetMapping("/task/{taskId}")
        public String getComentariosByTask(@PathVariable Long taskId, Model model) {
            Optional<Task> taskOpt = taskRepository.findById(taskId);
            if (taskOpt.isEmpty()) {
                model.addAttribute("error", "Tarea no encontrada");
                return "redirect:/tasks";
            }

            Task task = taskOpt.get();
            List<Comment> comentarios = comentarioRepository.findByTaskOrderByFechaCreacionDesc(task);
            model.addAttribute("task", task);
            model.addAttribute("comentarios", comentarios);

            return "comentario/comentario-list";
        }


        @PostMapping("/task/{taskId}/add")
        public String addComentario(@PathVariable Long taskId,
                                    @RequestParam String descripcion,
                                    Principal principal,
                                    Model model) {
            Optional<Task> taskOpt = taskRepository.findById(taskId);
            if (taskOpt.isEmpty()) {
                model.addAttribute("error", "Tarea no encontrada");
                return "redirect:/tasks";
            }

            String username = principal.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            Task task = taskOpt.get();


            if (!task.getProject().getUsers().contains(user)) {
                model.addAttribute("error", "No tienes permiso para comentar esta tarea");
                return "redirect:/tasks/" + taskId;
            }

            Comment comentario = new Comment();
            comentario.setDescripcion(descripcion);
            comentario.setTask(task);
            comentario.setUsuario(user);
            comentarioRepository.save(comentario);

            return "redirect:/tasks/" + taskId;
        }
}
