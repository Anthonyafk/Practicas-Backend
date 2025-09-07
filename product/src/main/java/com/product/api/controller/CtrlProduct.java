package com.product.api.controller;
import com.product.api.entity.Category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class CtrlProduct {

    @GetMapping("/category")
    public List<Category> getCategories() {
        return List.of(
            new Category(1, "Lentes", "Lts", 1),
            new Category(2, "Relojes", "Rljs", 1),
            new Category(3, "Billeteras", "Bllts", 1),
            new Category(4, "Mochilas", "Mchls", 1),
            new Category(5, "Peluches", "Pelch", 1),
            new Category(6, "Papas", "Paps", 1)
        );
    }
}