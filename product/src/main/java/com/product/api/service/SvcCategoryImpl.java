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
    public ResponseEntity<List<Category>> findAll() {
        try {
            List<Category> categories = repoCategory.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos: " + e.getMessage());
        }
    }
}