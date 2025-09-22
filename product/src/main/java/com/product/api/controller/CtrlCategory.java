package com.product.api.controller;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para manejar las operaciones CRUD de categorías.
 */
@RestController
@RequestMapping("/Category")
@CrossOrigin(origins = "*")
public class CtrlCategory {

    private final SvcCategory svcCategory;

    @Autowired
    public CtrlCategory(SvcCategory svcCategory) {
        this.svcCategory = svcCategory;
    }

    /**
     * Obtiene todas las categorías activas de la base de datos.
     * 
     * @return ResponseEntity con la lista de categorías activas
     */
    @GetMapping("")
    public ResponseEntity<List<Category>> findAll() {
        return svcCategory.findAll();
    }
}