package com.product.api.service;

import com.product.api.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio para manejar la lógica de negocio de las categorías.
 */
public interface SvcCategory {

    /**
     * Obtiene todas las categorías activas.
     * 
     * @return ResponseEntity con la lista de categorías activas
     */
    ResponseEntity<List<Category>> findAll();
}