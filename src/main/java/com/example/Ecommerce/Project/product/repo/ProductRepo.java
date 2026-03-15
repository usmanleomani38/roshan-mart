package com.example.Ecommerce.Project.product.repo;

import com.example.Ecommerce.Project.cart.model.Cart;
import com.example.Ecommerce.Project.category.model.Category;
import com.example.Ecommerce.Project.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    boolean existsByProductName(String productName);

    Page<Product> findByCategory(Category category, Pageable pageDetails);

    boolean existsByProductNameIgnoreCaseAndCategory(String productName, Category category);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

//    @Query("""
//            SELECT c
//            FROM Cart c
//            WHERE c.cartId IN (
//               SELECT ci.cart.cartId
//               FROM CartItem ci
//               WHERE ci.product.productId = :productId)
//               """)
//    List<Cart> findbyProductId(@Param("productId") Long productId);

}