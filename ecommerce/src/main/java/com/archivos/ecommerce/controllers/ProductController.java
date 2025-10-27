package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.NewProductDto;
import com.archivos.ecommerce.dtos.ProductDto;
import com.archivos.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyRole('MODERATOR')")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getByCategory(@PathVariable Integer categoryId){
        return ResponseEntity.ok(productService.findByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody NewProductDto dto){
        return ResponseEntity.status(201).body(productService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable UUID id, @RequestBody NewProductDto dto){
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ProductDto>> getApproved() {
        return ResponseEntity.ok(productService.getApprovedProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
