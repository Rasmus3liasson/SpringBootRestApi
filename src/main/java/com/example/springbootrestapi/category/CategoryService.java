package com.example.springbootrestapi.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<CategoryEntity> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public boolean categoryExist(String categoryName) {
        return categoryRepository.findAll().stream()
                .anyMatch(category -> category.getName().equalsIgnoreCase(categoryName));
    }

    public List<CategoryEntity> createCategory(CategoryEntity newCategory) {
        if (!categoryExist(newCategory.getName())) {
            categoryRepository.save(newCategory);
        }
        return getAllCategories();
    }
}
