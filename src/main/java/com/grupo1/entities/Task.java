package com.grupo1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tareas")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 400)
    private String descripcion;

    private Boolean completada;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Project project;


    public Task() {}

    public Task(String titulo, String descripcion, Boolean completada, Project project) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }

    public Project getProyecto() {
        return project;
    }

    public void setProyecto(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", completada=" + completada +
                ", proyecto=" + project +
                '}';
    }
}
