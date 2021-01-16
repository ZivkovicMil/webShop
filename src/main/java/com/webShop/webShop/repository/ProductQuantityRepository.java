package com.webShop.webShop.repository;

import com.webShop.webShop.entities.ProductQuantity;
import com.webShop.webShop.entities.ProductQuantityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, ProductQuantityKey> {

    @Modifying
    @Transactional
    @Query(value = "delete from product_quantity where order_id=:order_id and product_id =:product_id", nativeQuery = true)
    void deleteByOrder(@Param("order_id") Integer order_id, @Param("product_id") Integer product_id);
}
