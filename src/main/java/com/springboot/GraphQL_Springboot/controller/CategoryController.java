package com.springboot.GraphQL_Springboot.controller;

import com.springboot.GraphQL_Springboot.dto.CategoryInput;
import com.springboot.GraphQL_Springboot.model.Category;
import com.springboot.GraphQL_Springboot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @QueryMapping
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @QueryMapping
    public Optional<Category> findCategoryById(@Argument Long id) {
        return categoryRepository.findById(id);
    }

    @MutationMapping
    @Transactional
    public Category createCategory(@Argument CategoryInput category) {
        var newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImages(category.getImages());
        return categoryRepository.save(newCategory);
    }

    @MutationMapping
    @Transactional
    public Category updateCategory(@Argument Long id, @Argument CategoryInput category) {
        var existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        existingCategory.setName(category.getName());
        existingCategory.setImages(category.getImages());
        return categoryRepository.save(existingCategory);
    }

    @MutationMapping
    @Transactional
    public boolean deleteCategory(@Argument Long id) {
        if (categoryRepository.existsById(id)) {
            // Cần xử lý logic xóa user khỏi category trước khi xóa category
            var category = categoryRepository.findById(id).get();
            category.getUsers().forEach(user -> user.getCategories().remove(category));
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}