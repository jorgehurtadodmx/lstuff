package com.grupo1.entities;

import com.grupo1.enums.UserRole;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "idUser", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String surname;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password; //Contraseña encriptada

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true; //activar o desactivar usuario

    @Column(name = "telephone")
    private String phone;

    @Enumerated(EnumType.STRING)//Guardar el Rol como texto
    @Column(nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER; //Por defecto el rol es USER

    @ManyToMany //varios usuarios pueden pertenecer a varios proyectos y viceversa.
    @JoinTable(name = "user_project")
    @Builder.Default
    private List<Project> projects = new ArrayList<>();
}