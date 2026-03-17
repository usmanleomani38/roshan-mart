package com.example.Ecommerce.Project.cart.repo;

import com.example.Ecommerce.Project.cart.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2")
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItemsList ci WHERE ci.product.productId = :productId")
    List<Cart> findCartsByProductId(@Param("productId") Long productId);

   // @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId")
    Optional<Cart> findByUser_UserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cart c WHERE c.user.userId = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user.userId = :userId")
    void deleteCartByUserId(@Param("userId") Long userId);

    boolean existsByUser_UserId(Long userId);

}

