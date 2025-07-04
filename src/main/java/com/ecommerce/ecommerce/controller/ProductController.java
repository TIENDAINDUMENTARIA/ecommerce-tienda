package com.ecommerce.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.product.ProductCreateMultipartDto;
import com.ecommerce.ecommerce.dto.product.ProductDto;
import com.ecommerce.ecommerce.service.product.ProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createProduct(@ModelAttribute @Valid ProductCreateMultipartDto productCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productCreateDto));
    }

    @GetMapping()
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id_product){
        return ResponseEntity.ok().body(productService.getProductById(id_product));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id_product, @ModelAttribute @Valid ProductCreateMultipartDto productCreateDto){
        return ResponseEntity.ok().body(productService.updateProduct(id_product, productCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id_product){
        productService.deleteProduct(id_product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
