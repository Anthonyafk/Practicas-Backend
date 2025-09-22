package com.product.api.repository;

import com.product.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio para manejar operaciones de la entidad Category.
 * Extiende JpaRepository para obtener operaciones CRUD básicas.
 */
@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {

    /**
     * Obtiene todas las categorías activas (status = 1).
     * 
     * @return Lista de categorías activas
     */
    @Query("SELECT c FROM Category c WHERE c.status = 1")
    List<Category> findAll();

}