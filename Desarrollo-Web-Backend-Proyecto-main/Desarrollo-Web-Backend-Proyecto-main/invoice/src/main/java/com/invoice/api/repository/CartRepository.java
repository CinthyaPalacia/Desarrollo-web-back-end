package com.invoice.api.repository;

import com.invoice.api.entity.Cart;
import com.invoice.api.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository public interface CartRepository extends JpaRepository<Cart, Integer>
{

    List<Cart> findByRfcAndStatus(String rfc, Integer status);

    @Query(value = "SELECT * FROM cart WHERE gtin = :gtin AND status = 1 AND rfc = :rfc", nativeQuery = true)
    Cart findCartbygtinAndRfc(@Param("gtin") String gtin, @Param("rfc") String rfc);

    @Query(value = "SELECT * FROM item WHERE gtin = :gtin AND status = 1", nativeQuery = true)
    Item getItembyGTIN(@Param("gtin") String gtin);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cart SET quantity = :quantity WHERE gtin = :gtin AND rfc = :rfc AND status = 1", nativeQuery = true)
    void updateCartQuantity(@Param("quantity") Integer quantity, @Param("gtin") String gtin, @Param("rfc") String rfc);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cart SET status = 0 WHERE cart_id = :cart_id AND status = 1", nativeQuery = true)
    Integer removeFromCart(@Param("cart_id") Integer cart_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cart SET status = 0 WHERE rfc = :rfc AND status = 1", nativeQuery = true)
    Integer clearCart(@Param("rfc") String rfc);
}
