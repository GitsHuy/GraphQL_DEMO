package com.springboot.GraphQL_Springboot.controller;

import com.springboot.GraphQL_Springboot.dto.ProductInput;
import com.springboot.GraphQL_Springboot.model.Product;
import com.springboot.GraphQL_Springboot.repository.ProductRepository;
import com.springboot.GraphQL_Springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @QueryMapping
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Optional<Product> findProductById(@Argument Long id) {
        return productRepository.findById(id);
    }

    @QueryMapping
    public List<Product> productsSortedByPrice(@Argument String sortDirection) {
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return productRepository.findAll(Sort.by(direction, "price"));
    }

    @QueryMapping
    public List<Product> productsByCategory(@Argument Long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId);
    }

    @MutationMapping
    @Transactional
    public Product createProduct(@Argument ProductInput product) {
        var user = userRepository.findById(product.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var newProduct = new Product();
        newProduct.setTitle(product.getTitle());
        newProduct.setDesc(product.getDesc());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());
        newProduct.setUser(user);
        return productRepository.save(newProduct);
    }

    @MutationMapping
    @Transactional
    public Product updateProduct(@Argument Long id, @Argument ProductInput product) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        var user = userRepository.findById(product.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingProduct.setTitle(product.getTitle());
        existingProduct.setDesc(product.getDesc());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setUser(user);
        return productRepository.save(existingProduct);
    }

    @MutationMapping
    @Transactional
    public boolean deleteProduct(@Argument Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}