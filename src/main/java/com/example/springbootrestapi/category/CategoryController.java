package com.example.springbootrestapi.category;


import com.example.springbootrestapi.place.PlaceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Optional<CategoryEntity> getCategoryById(@PathVariable Integer id) {
        Optional<CategoryEntity> category = categoryService.getCategoryById(id);

        if (category.isPresent()) {
            return category;
        } else {
            throw new IllegalArgumentException("Category with id " + id + " does not exist");
        }
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createCategory(@RequestBody CategoryEntity categoryEntity) {

        if (categoryService.categoryExist(categoryEntity.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
        }

        categoryService.createCategory(categoryEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created category successfully");
    }


}
