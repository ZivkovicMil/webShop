package com.webShop.webShop.repository;

import com.webShop.webShop.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    @Query(value = "select * from product_image where product_image_id = :product_image_id", nativeQuery = true)
    ProductImage findByName(@Param("product_image_id") String hash);

    @Query(value = "select * from product_image where product_id = :product_id and image_number = :image_number", nativeQuery = true)
    ProductImage findImageByImageNumber(@Param("product_id") Integer product_id, @Param("image_number") Integer image_number);

    @Query(value = "select * from product_image where product_id = :product_id", nativeQuery = true)
    List<ProductImage> findAllProductImages(@Param("product_id") Integer product_id);
}
