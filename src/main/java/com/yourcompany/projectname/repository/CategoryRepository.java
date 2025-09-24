package com.yourcompany.projectname.repository;

import com.yourcompany.projectname.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
