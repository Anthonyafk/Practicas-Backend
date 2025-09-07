package com.product.api.service.impl;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Category> getCategories() {
        return repoCategory.getCategories();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Integer id) {
        return repoCategory.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
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
    }

    @Override
    public Category updateCategory(Integer id, Category category) {
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
    }

    @Override
    public boolean deleteCategory(Integer id) {
        Optional<Category> category = repoCategory.findById(id);
        if (category.isPresent()) {
            Category categoryToDelete = category.get();
            categoryToDelete.setStatus(0); // Eliminación lógica
            repoCategory.save(categoryToDelete);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> searchCategoriesByName(String name) {
        return repoCategory.findByCategoryContainingIgnoreCaseAndStatus(name, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getActiveCategoriesCount() {
        return repoCategory.countActiveCategories();
    }
}