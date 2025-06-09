package com.grupo1.repositories;

import com.grupo1.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    //Project findByNombreIgnoreCase(String nombre);


}