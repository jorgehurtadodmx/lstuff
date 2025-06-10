package com.grupo1.repositories;

import com.grupo1.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}