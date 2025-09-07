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
    List<Category> getCategories();

    /**
     * Busca categorías activas por nombre.
     * 
     * @param category Nombre de la categoría
     * @return Lista de categorías que coinciden con el nombre
     */
    List<Category> findByCategoryContainingIgnoreCaseAndStatus(String category, Integer status);

    /**
     * Busca una categoría por su tag.
     * 
     * @param tag Tag de la categoría
     * @return Optional con la categoría encontrada
     */
    Optional<Category> findByTag(String tag);

    /**
     * Busca una categoría por su nombre exacto.
     * 
     * @param category Nombre exacto de la categoría
     * @return Optional con la categoría encontrada
     */
    Optional<Category> findByCategory(String category);

    /**
     * Cuenta las categorías activas.
     * 
     * @return Número de categorías activas
     */
    @Query("SELECT COUNT(c) FROM Category c WHERE c.status = 1")
    Long countActiveCategories();

    /**
     * Obtiene todas las categorías ordenadas por nombre.
     * 
     * @return Lista de todas las categorías ordenadas por nombre
     */
    List<Category> findAllByOrderByCategoryAsc();
}