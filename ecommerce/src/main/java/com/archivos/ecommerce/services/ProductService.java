package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.NewProductDto;
import com.archivos.ecommerce.dtos.ProductDto;
import com.archivos.ecommerce.entities.Category;
import com.archivos.ecommerce.entities.Product;
import com.archivos.ecommerce.entities.State;
import com.archivos.ecommerce.repositories.CategoryRepository;
import com.archivos.ecommerce.repositories.ProductRepository;
import com.archivos.ecommerce.repositories.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StateRepository stateRepository;


    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no econtrado"));
        return convertToDto(product);
    }

    //Metodo para crear producto desde DTO de entrada
    public ProductDto create(NewProductDto dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        State state = stateRepository.findById(dto.stateId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPicture(dto.picture());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setCategory(category);
        product.setState(state);

        Product saved = productRepository.save(product);
        return convertToDto(saved);
    }

    public ProductDto update(UUID id, NewProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Category category = categoryRepository.findById(dto.categoryId())
                        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        State state = stateRepository.findById(dto.stateId())
                        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPicture(dto.picture());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setCategory(category);
        product.setState(state);

        Product updated = productRepository.save(product);
        return convertToDto(updated);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    //Metodo para buscar productos por categoria
    public List<ProductDto> findByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //Metodo conversor privado a DTO'S
    private ProductDto convertToDto(Product product){
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPicture(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getState() != null ? product.getState().getName() : null
        );
    }
}