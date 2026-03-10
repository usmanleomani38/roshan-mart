package com.example.Ecommerce.Project.category.categoryrepo;

import com.example.Ecommerce.Project.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    boolean existsByCategoryName(String categoryName);
    List<Category> findByCategoryNameIn(List<String> categoryName);
}
