package com.grupo1.repositories;

import com.grupo1.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    Proyecto findByNombreIgnoreCase(String nombre);


}