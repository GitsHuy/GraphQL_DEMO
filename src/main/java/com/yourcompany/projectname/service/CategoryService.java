package com.yourcompany.projectname.service;

import com.yourcompany.projectname.dto.CategoryInput;
import com.yourcompany.projectname.model.Category;
import com.yourcompany.projectname.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public Category createCategory(CategoryInput categoryInput) {
        Category newCategory = new Category();
        newCategory.setName(categoryInput.getName());
        newCategory.setImages(categoryInput.getImages());
        return categoryRepository.save(newCategory);
    }

    @Transactional
    public Category updateCategory(Long id, CategoryInput categoryInput) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
        existingCategory.setName(categoryInput.getName());
        existingCategory.setImages(categoryInput.getImages());
        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            // Cảnh báo: Xóa category có thể làm các product liên quan bị lỗi
            // Cần có logic xử lý nâng cao ở đây trong thực tế
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
