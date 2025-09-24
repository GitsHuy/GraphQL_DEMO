package com.springboot.GraphQL_Springboot.repository;

import com.springboot.GraphQL_Springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Tìm sản phẩm dựa trên categoryId.
    // Query này tìm tất cả các user thuộc category, sau đó tìm tất cả sản phẩm của các user đó.
    @Query("SELECT p FROM Product p WHERE p.user.id IN (SELECT u.id FROM User u JOIN u.categories c WHERE c.id = :categoryId)")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);
}