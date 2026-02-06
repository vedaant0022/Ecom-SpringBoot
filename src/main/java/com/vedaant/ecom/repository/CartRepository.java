package com.vedaant.ecom.repository;

import com.vedaant.ecom.DTO.CartItemDTO;
import com.vedaant.ecom.model.Cart;
import com.vedaant.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUserId(int userId);

    @Query("""
                SELECT new com.vedaant.ecom.DTO.CartItemDTO(
                    p.id,
                    p.name,
                    p.desc,
                    p.price,
                    ci.quantity
                )
                FROM Cart c
                JOIN c.items ci
                JOIN ci.product p
                WHERE c.user.id = :userId
            """)
    List<CartItemDTO> getCartItems(@Param("userId") int userId);

    // 🔼 Increase quantity
    @Query("""
                UPDATE CartItem ci
                SET ci.quantity = ci.quantity + 1
                WHERE ci.cart.user.id = :userId
                  AND ci.product.id = :productId
            """)
    int increaseQuantity(@Param("userId") int userId,
                         @Param("productId") int productId);

    @Modifying
    @Query("""
                UPDATE CartItem ci
                SET ci.quantity = ci.quantity - 1
                WHERE ci.cart.user.id = :userId
                  AND ci.product.id = :productId
                  AND ci.quantity > 1
            """)
    int decreaseQuantity(@Param("userId") int userId,
                         @Param("productId") int productId);

    @Modifying
    @Query("""
                DELETE FROM CartItem ci
                WHERE ci.cart.user.id = :userId
                  AND ci.product.id = :productId
            """)
    int removeProduct(@Param("userId") int userId,
                      @Param("productId") int productId);

    @Query("""
    SELECT COALESCE(SUM(p.price * ci.quantity), 0)
    FROM Cart c
    JOIN c.items ci
    JOIN ci.product p
    WHERE c.user.id = :userId
""")
    BigDecimal calculateTotal(@Param("userId") int userId);

}
