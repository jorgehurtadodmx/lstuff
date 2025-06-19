package com.grupo1.controllers;

import com.grupo1.entities.Comment;
import com.grupo1.entities.Project;
import com.grupo1.entities.Task;
import com.grupo1.repositories.CommentRepository;
import com.grupo1.repositories.ProjectRepository;
import com.grupo1.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    @GetMapping
    public String getAllComments(Model model) {
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        return "comment/comment-list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            model.addAttribute("comment", comment.get());
        } else {
            model.addAttribute("error", "comentario no encontrado");
        }
        return "comment/comment-detail";
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute Comment comment) {
        commentRepository.save(comment);
        return "redirect:/comments";
    }

    @GetMapping("/{id}/edit")
    public String editCommentForm(@PathVariable Long id, Model model) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            model.addAttribute("comment", comment.get());
            model.addAttribute("projects", commentRepository.findAll());
            return "comment/comment-form";

        } else {
            model.addAttribute("error", "comentario no encontrado");
        }
        return "redirect:/comments";
    }

    @PostMapping
    public String saveComment(@ModelAttribute Comment comment,
            @RequestParam("taskId") Long taskId) {
        taskRepository.findById(taskId).ifPresent(comment::setTask);
        commentRepository.save(comment);
        return "redirect:/comments";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("tasks", taskRepository.findAll());
        return "comment/comment-form";
    }

    @PostMapping("/{id}")
    public String updateComment(@ModelAttribute Comment comment) {
        commentRepository.save(comment);
        return "redirect:/comments";
    }

    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return "redirect:/comments";
    }
}


