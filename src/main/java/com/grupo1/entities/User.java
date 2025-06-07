package com.grupo1.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString(exclude = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "idUsuario", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "apellido", nullable = false)
    private String surname;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password; //Contraseña encriptada

    @Column(name = "activo", nullable = false)
    private Boolean active = true; //activar o desactivar usuario

    @Column(name = "telefono")
    private String phone;

    @Enumerated(EnumType.STRING)//Guardar el Rol como texto
    @Column(nullable = false)
    private UserRole role;

    @ManyToMany //varios usuarios pueden pertenecer a varios proyectos y viceversa.
    @JoinTable(name = "user_project")
    @Builder.Default
    private List<Project> projects = new ArrayList<>();
}
