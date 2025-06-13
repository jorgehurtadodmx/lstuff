package com.grupo1.entities;

import com.grupo1.enums.Priority;
import com.grupo1.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    //due_date
    @Column(name = "due_date")
    private LocalDate dueDate;

    //created_at
    private LocalDate createdAt;

    //updated_at
    private LocalDate updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus = TaskStatus.ABIERTA;

    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /*@ManyToOne
    @JoinColumn(name = "assigned_to_user_id", columnDefinition = "BINARY(16)")
    private User assignedToUser;*/

    public Task() {}


    public Task(Long id, String title, String description, LocalDate dueDate, LocalDate createdAt, LocalDate updatedAt, Priority priority, TaskStatus taskStatus, Project project) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.priority = priority;
        this.taskStatus = taskStatus;
        this.project = project;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", taskStatus=" + taskStatus +
                ", priority=" + priority +
                ", project=" + project +
                '}';
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
