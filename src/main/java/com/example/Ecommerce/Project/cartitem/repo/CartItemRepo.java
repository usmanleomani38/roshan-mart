package com.example.Ecommerce.Project.cartitem.repo;

import com.example.Ecommerce.Project.cartitem.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
   // boolean existsByCartIdAndProductId(Long productId, Long cardId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    Optional<CartItem> findCartItemByCartIdAndProductId(Long cartId, Long productId);


    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    void deleteCartItemByCartIdAndProductId(Long cartId, Long productId);

}
