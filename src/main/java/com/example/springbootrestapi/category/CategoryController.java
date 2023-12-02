package com.example.springbootrestapi.category;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/categories", produces = "application/json")
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
            throw new EntityNotFoundException("Category with id " + id + " does not exist");
        }
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize("#oauth2.hasScope('admin')")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryEntity categoryEntity) {
        try {
            if (categoryService.categoryExist(categoryEntity.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
            } else if (!categoryEntity.getSymbol().matches("^[\\x{1F300}-\\x{1F6FF}\\x{1F700}-\\x{1F77F}\\x{1F780}-\\x{1F7FF}\\x{1F800}-\\x{1F8FF}\\x{1F900}-\\x{1F9FF}\\x{2600}-\\x{26FF}]$")) {
                throw new IllegalArgumentException("Not a valid symbol");
            }

            categoryService.createCategory(categoryEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created category successfully");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Not a valid post request");

        }
    }
}
