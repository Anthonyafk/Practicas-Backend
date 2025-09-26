package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para recibir datos de entrada al crear o actualizar una categoría.
 * Solo incluye los campos necesarios (sin ID ni status).
 */
public class DtoCategoryIn {

    @JsonProperty("category")
    @NotNull(message = "La categoría es obligatoria")
    private String category;

    @JsonProperty("tag")
    @NotNull(message = "El tag es obligatorio")
    private String tag;

    /**
     * Constructor por defecto.
     */
    public DtoCategoryIn() {
    }

    /**
     * Constructor con parámetros.
     * 
     * @param category Nombre de la categoría
     * @param tag Tag o abreviatura de la categoría
     */
    public DtoCategoryIn(String category, String tag) {
        this.category = category;
        this.tag = tag;
    }

    // Getters y Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}