package com.grupo1.repositories;

import com.grupo1.entities.Project;
import com.grupo1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUsersUsername(String username);
}