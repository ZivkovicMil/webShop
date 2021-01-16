package com.webShop.webShop.repository;

import com.webShop.webShop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select * from category where category_name=:category_name ", nativeQuery = true)
    Category findByName(@Param("category_name") String category);
}
