package com.webShop.webShop.repository;

import com.webShop.webShop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product where product_id = :product_id", nativeQuery = true)
    Product findByIdd(@Param("product_id") int product_id);

    @Query(value = "select * from product where category_id = :category_id", nativeQuery = true)
    Page<Product> findByCategory(@Param("category_id") Integer category, Pageable pageable);
}
