package com.grupo1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String comment;

    //    @Column(name = "due_date")
    //    private LocalDate dueDate;
    //
    //    private LocalDate createAt;
    //
    //    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

}