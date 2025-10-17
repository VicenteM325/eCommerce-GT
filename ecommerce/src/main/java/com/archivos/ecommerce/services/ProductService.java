package com.archivos.ecommerce.services;

import com.archivos.ecommerce.entities.Product;
import com.archivos.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(UUID id) {
        return productRepository.findById(id);
    }

    public Product create(Product product) {
        product.setProductId(UUID.randomUUID());
        return productRepository.save(product);
    }

    public Product update(UUID id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPicture(productDetails.getPicture());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setCategory(productDetails.getCategory());
        product.setState(productDetails.getState());

        return productRepository.save(product);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }
}