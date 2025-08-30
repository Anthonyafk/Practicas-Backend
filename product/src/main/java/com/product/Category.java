package com.product;

/* Class Category.java */

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Category representa una categoría con identificador, nombre, tag y
 * estatus.
 * Permite gestionar una lista de categorías en memoria, asegurando unicidad en
 * los atributos principales.
 */
public class Category {
    /**
     * Identificador único de la categoría.
     */
    int category_id;

    /**
     * Nombre de la categoría. Debe ser único.
     */
    String category;

    /**
     * Tag o abreviatura de la categoría. Debe ser único.
     */
    String tag;

    /**
     * Estatus de la categoría (1 = activa, 0 = eliminada).
     */
    int status;

    /**
     * Constructor para crear una nueva categoría.
     * 
     * @param category_id Identificador único.
     * @param category    Nombre de la categoría.
     * @param tag         Tag o abreviatura.
     * @param status      Estatus (1 = activa, 0 = eliminada).
     */
    public Category(int category_id, String category, String tag, int status) {
        this.category_id = category_id;
        this.category = category;
        this.tag = tag;
        this.status = status;
    }


    // Getters y Setters (necesarios para Jackson)
    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    /**
     * Lista estática que almacena todas las categorías creadas en tiempo de
     * ejecución.
     */
    static List<Category> categories = new ArrayList<>();

    /**
     * Muestra en consola todas las categorías activas (estatus 1).
     * Si no hay categorías, muestra un mensaje indicándolo.
     */
    public static void getCategories() {
        List<String> resultado = new ArrayList<>();
        for (Category c : categories) {
            if (c.status == 1) {
                resultado.add("{" + c.category_id + ",\"" + c.category + "\",\"" + c.tag + "\"," + c.status + "}");
            }
        }
        if (resultado.isEmpty()) {
            System.out.println("No existen categorías registradas");
        } else {
            System.out.println(resultado);
        }
    }

    /**
     * Registra una nueva categoría en la lista si sus atributos principales son
     * únicos.
     * 
     * @param category Objeto Category a registrar.
     * @return Nombre de la categoría registrada o mensaje de error si ya existe
     *         algún atributo.
     */
    public static String createCategory(Category category) {
        for (Category c : categories) {
            if (c.category_id == category.category_id ||
                    c.category.equals(category.category) ||
                    c.tag.equals(category.tag)) {
                return "Error: category_id, category o tag ya existen";
            }
        }
        categories.add(category);
        return category.category;
    }

    /**
     * Cambia el estatus a 0 (eliminada) de la categoría con el id dado.
     * 
     * @param category_id Identificador de la categoría a eliminar.
     * @return El id eliminado o -1 si no se encontró la categoría.
     */
    public static int deleteCategory(int category_id) {
        for (Category c : categories) {
            if (c.category_id == category_id) {
                c.status = 0;
                return category_id;
            }
        }
        return -1;
    }
}