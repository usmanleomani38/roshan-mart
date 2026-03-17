package com.example.Ecommerce.Project.cartitem.repo;

import com.example.Ecommerce.Project.cart.model.Cart;
import com.example.Ecommerce.Project.cartitem.model.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
   // boolean existsByCartIdAndProductId(Long productId, Long cardId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    Optional<CartItem> findCartItemByCartIdAndProductId(Long cartId, Long productId);


    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    void deleteCartItemByCartIdAndProductId(Long cartId, Long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.user.userId = :userId")
    void deleteCartItemsByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cartId = ?1")
    void deleteByCartId(Long cartId);




    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.product.productId = :productId")
    void deleteCartItemByProductId(@Param("productId") Long productId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.product.productId = :productId")
    Optional<CartItem> findCartItemByProductId(@Param("productId") Long productId);
}
