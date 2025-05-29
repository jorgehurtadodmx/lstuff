package com.grupo1.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 400)
    private String descripcion;

    private LocalDate fechaInicio;

    private Boolean activo;

    public Proyecto() {}

    public Proyecto(Boolean activo, LocalDate fecha, String descripcion, String nombre) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fecha;
        this.activo = activo;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fechaInicio;
    }

    public void setFecha(LocalDate fecha) {
        this.fechaInicio = fecha;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "Id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fechaInicio +
                ", activo=" + activo +
                '}';
    }
}