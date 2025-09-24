package com.yourcompany.projectname.repository;

import com.yourcompany.projectname.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Tự động tạo câu lệnh SELECT ... WHERE category_id = ?
    List<Product> findByCategoryId(Long categoryId);
}
