package com.webShop.webShop.repository;

import com.webShop.webShop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "select * from xorder where order_id=:order_id", nativeQuery = true)
    Order findByIdd(@Param("order_id") int order_id);

    @Query(value = "select * from xorder where user_id = :user_id order by order_created desc limit 1", nativeQuery = true)
    Order findLastOrder(@Param("user_id") int user_id);
}
