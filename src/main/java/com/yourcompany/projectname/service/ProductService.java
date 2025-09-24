package com.yourcompany.projectname.service;

import com.yourcompany.projectname.dto.ProductInput;
import com.yourcompany.projectname.model.Product;
import com.yourcompany.projectname.repository.CategoryRepository;
import com.yourcompany.projectname.repository.ProductRepository;
import com.yourcompany.projectname.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllSortedByPrice(String sortDirection) {
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return productRepository.findAll(Sort.by(direction, "price"));
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Transactional
    public Product createProduct(ProductInput productInput) {
        var user = userRepository.findById(productInput.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + productInput.getUserId()));
        var category = categoryRepository.findById(productInput.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + productInput.getCategoryId()));

        Product newProduct = new Product();
        newProduct.setTitle(productInput.getTitle());
        newProduct.setDesc(productInput.getDesc());
        newProduct.setPrice(productInput.getPrice());
        newProduct.setQuantity(productInput.getQuantity());
        newProduct.setUser(user);
        newProduct.setCategory(category);
        return productRepository.save(newProduct);
    }

    @Transactional
    public Product updateProduct(Long id, ProductInput productInput) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        var user = userRepository.findById(productInput.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + productInput.getUserId()));
        var category = categoryRepository.findById(productInput.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + productInput.getCategoryId()));

        existingProduct.setTitle(productInput.getTitle());
        existingProduct.setDesc(productInput.getDesc());
        existingProduct.setPrice(productInput.getPrice());
        existingProduct.setQuantity(productInput.getQuantity());
        existingProduct.setUser(user);
        existingProduct.setCategory(category);
        return productRepository.save(existingProduct);
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
