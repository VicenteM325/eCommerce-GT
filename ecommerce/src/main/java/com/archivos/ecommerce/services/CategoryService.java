package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.CategoryDto;
import com.archivos.ecommerce.dtos.NewCategoryDto;
import com.archivos.ecommerce.entities.Category;
import com.archivos.ecommerce.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //Obtener categoria por ID
    public CategoryDto getById(Integer id) {
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return convertToDto(category);
    }

    public CategoryDto create(NewCategoryDto dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());

        Category saved = categoryRepository.save(category);
        return convertToDto(saved);
    }

    public CategoryDto update(Integer id, NewCategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        category.setName(dto.name());
        category.setDescription(dto.description());

        Category updated =categoryRepository.save(category);
        return convertToDto(updated);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    //Metodo conversor privado a DTO'S
    private CategoryDto convertToDto(Category category){
        return new CategoryDto(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }
}