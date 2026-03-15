package com.example.Ecommerce.Project.cart.repo;

import com.example.Ecommerce.Project.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItemsList ci WHERE ci.product.productId = :productId")
    List<Cart> findCartsByProductId(@Param("productId") Long productId);

}

