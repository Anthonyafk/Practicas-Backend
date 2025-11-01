package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.service.SvcProductImage;
import com.product.commons.dto.ApiResponse;
import com.product.exception.ApiException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class CtrlProductImage {
	
    @Autowired
    SvcProductImage svc;

    @PostMapping("/{id}/image")
    public ResponseEntity<ApiResponse> createProductImage(
    		@PathVariable("id") Integer productId, 
    		@Valid @RequestBody DtoProductImageIn in) {
        return ResponseEntity.ok(svc.upload(productId, in));
    }
    
    @GetMapping("/{id}/image")
    public ResponseEntity<List<ProductImage>> getProductImages(
    		@PathVariable("id") Integer productId) {
    	return ResponseEntity.ok(svc.getImages(productId));
    }
    
    @DeleteMapping("/{id}/image/{product-image-id}")
    public ResponseEntity<ApiResponse> deleteProductImage(
    		@PathVariable("id") Integer productId, 
    		@PathVariable("product-image-id") Integer productImageId) {
    	return ResponseEntity.ok(svc.deleteImage(productId, productImageId));
    }

}