package com.product.api.service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para manejar la lógica de negocio de las categorías.
 */
@Service
@Transactional
public class SvcCategoryImpl implements SvcCategory {

    private final RepoCategory repoCategory;

    @Autowired
    public SvcCategoryImpl(RepoCategory repoCategory) {
        this.repoCategory = repoCategory;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<Category>> getCategories() {
        try {
            List<Category> categories = repoCategory.getCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Integer id) {
        try {
            return repoCategory.findById(id);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    @Override
    public Category createCategory(Category category) {
        try {
            // Validar que no exista una categoría con el mismo nombre
            Optional<Category> existingByName = repoCategory.findByCategory(category.getCategory());
            if (existingByName.isPresent()) {
                throw new RuntimeException("Ya existe una categoría con el nombre: " + category.getCategory());
            }

            // Validar que no exista una categoría con el mismo tag
            Optional<Category> existingByTag = repoCategory.findByTag(category.getTag());
            if (existingByTag.isPresent()) {
                throw new RuntimeException("Ya existe una categoría con el tag: " + category.getTag());
            }

            // Si no se especifica status, se asigna 1 (activa) por defecto
            if (category.getStatus() == null) {
                category.setStatus(1);
            }

            return repoCategory.save(category);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    @Override
    public Category updateCategory(Integer id, Category category) {
        try {
            Optional<Category> existingCategory = repoCategory.findById(id);
            if (!existingCategory.isPresent()) {
                throw new RuntimeException("No se encontró la categoría con ID: " + id);
            }

            Category categoryToUpdate = existingCategory.get();

            // Validar que el nuevo nombre no esté en uso por otra categoría
            if (!categoryToUpdate.getCategory().equals(category.getCategory())) {
                Optional<Category> existingByName = repoCategory.findByCategory(category.getCategory());
                if (existingByName.isPresent() && !existingByName.get().getCategoryId().equals(id)) {
                    throw new RuntimeException("Ya existe una categoría con el nombre: " + category.getCategory());
                }
            }

            // Validar que el nuevo tag no esté en uso por otra categoría
            if (!categoryToUpdate.getTag().equals(category.getTag())) {
                Optional<Category> existingByTag = repoCategory.findByTag(category.getTag());
                if (existingByTag.isPresent() && !existingByTag.get().getCategoryId().equals(id)) {
                    throw new RuntimeException("Ya existe una categoría con el tag: " + category.getTag());
                }
            }

            // Actualizar los campos
            categoryToUpdate.setCategory(category.getCategory());
            categoryToUpdate.setTag(category.getTag());
            if (category.getStatus() != null) {
                categoryToUpdate.setStatus(category.getStatus());
            }

            return repoCategory.save(categoryToUpdate);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteCategory(Integer id) {
        try {
            Optional<Category> category = repoCategory.findById(id);
            if (category.isPresent()) {
                Category categoryToDelete = category.get();
                categoryToDelete.setStatus(0); // Eliminación lógica
                repoCategory.save(categoryToDelete);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> searchCategoriesByName(String name) {
        try {
            return repoCategory.findByCategoryContainingIgnoreCaseAndStatus(name, 1);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getActiveCategoriesCount() {
        try {
            return repoCategory.countActiveCategories();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos: " + e.getMessage());
        }
    }
}