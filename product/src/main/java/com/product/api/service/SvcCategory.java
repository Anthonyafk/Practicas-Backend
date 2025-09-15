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
    ResponseEntity<List<Category>> getCategories();

    /**
     * Obtiene una categoría por su ID.
     * 
     * @param id ID de la categoría
     * @return Optional con la categoría encontrada
     */
    Optional<Category> getCategoryById(Integer id);

    /**
     * Crea una nueva categoría.
     * 
     * @param category Categoría a crear
     * @return Categoría creada
     * @throws RuntimeException si ya existe una categoría con el mismo nombre o tag
     */
    Category createCategory(Category category);

    /**
     * Actualiza una categoría existente.
     * 
     * @param id ID de la categoría a actualizar
     * @param category Datos actualizados de la categoría
     * @return Categoría actualizada
     * @throws RuntimeException si la categoría no existe
     */
    Category updateCategory(Integer id, Category category);

    /**
     * Elimina lógicamente una categoría (cambia status a 0).
     * 
     * @param id ID de la categoría a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    boolean deleteCategory(Integer id);

    /**
     * Busca categorías por nombre (búsqueda parcial).
     * 
     * @param name Nombre a buscar
     * @return Lista de categorías que contienen el nombre
     */
    List<Category> searchCategoriesByName(String name);

    /**
     * Obtiene el número total de categorías activas.
     * 
     * @return Número de categorías activas
     */
    Long getActiveCategoriesCount();
}