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
@RequestMapping("/api")
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
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategories() {
        return svcCategory.getCategories();
    }

    /**
     * Crea una nueva categoría.
     * 
     * @param category Categoría a crear
     * @return ResponseEntity con la categoría creada
     */
    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = svcCategory.createCategory(category);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza una categoría existente.
     * 
     * @param id ID de la categoría a actualizar
     * @param category Datos actualizados de la categoría
     * @return ResponseEntity con la categoría actualizada
     */
    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
        try {
            Category updatedCategory = svcCategory.updateCategory(id, category);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina lógicamente una categoría (cambia status a 0).
     * 
     * @param id ID de la categoría a eliminar
     * @return ResponseEntity con el resultado de la operación
     */
    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        try {
            boolean deleted = svcCategory.deleteCategory(id);
            if (deleted) {
                return new ResponseEntity<>("Categoría eliminada correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}