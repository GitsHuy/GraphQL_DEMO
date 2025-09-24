package com.springboot.GraphQL_Springboot.repository;

import com.springboot.GraphQL_Springboot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}