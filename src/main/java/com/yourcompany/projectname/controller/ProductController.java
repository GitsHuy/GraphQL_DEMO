package com.yourcompany.projectname.controller;

import com.yourcompany.projectname.dto.ProductInput;
import com.yourcompany.projectname.model.Product;
import com.yourcompany.projectname.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @QueryMapping
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @QueryMapping
    public Optional<Product> findProductById(@Argument Long id) {
        return productService.findById(id);
    }

    @QueryMapping
    public List<Product> productsSortedByPrice(@Argument String sortDirection) {
        return productService.findAllSortedByPrice(sortDirection);
    }

    @QueryMapping
    public List<Product> productsByCategory(@Argument Long categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    @MutationMapping
    public Product createProduct(@Argument("product") ProductInput productInput) {
        return productService.createProduct(productInput);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument("product") ProductInput productInput) {
        return productService.updateProduct(id, productInput);
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }
}
