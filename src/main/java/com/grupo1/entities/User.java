package com.grupo1.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "projects") // evita el bucle infinito al imprimir pryecto
@NoArgsConstructor // constructor vacío
@AllArgsConstructor // constructor con todos los parámetros
@Builder // permite crear objetos de forma más sencilla
@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long id;


    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "apellido", nullable = false)
    private String surname;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activo", nullable = false)
    private Boolean active;

    @Column(name = "telefono")
    private String phone;

    @Column(name="rol", nullable = false)
    private Set<Role> roles;

   //Muchos usuarios pueden pertenecer a muchos proyectos
   @ManyToMany
   @JoinTable(name = "user_project")
   @Builder.Default // inicializa la lista vacía por defecto (un arrayList vacío)
   private List<Project> projects = new ArrayList<>();

}
