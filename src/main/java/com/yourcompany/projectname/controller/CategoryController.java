package com.yourcompany.projectname.controller;

import com.yourcompany.projectname.dto.CategoryInput;
import com.yourcompany.projectname.model.Category;
import com.yourcompany.projectname.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @QueryMapping
    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }

    @QueryMapping
    public Optional<Category> findCategoryById(@Argument Long id) {
        return categoryService.findById(id);
    }

    @MutationMapping
    public Category createCategory(@Argument("category") CategoryInput categoryInput) {
        return categoryService.createCategory(categoryInput);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument("category") CategoryInput categoryInput) {
        return categoryService.updateCategory(id, categoryInput);
    }

    @MutationMapping
    public boolean deleteCategory(@Argument Long id) {
        return categoryService.deleteCategory(id);
    }
}
