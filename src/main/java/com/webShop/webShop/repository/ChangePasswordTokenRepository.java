package com.webShop.webShop.repository;

import com.webShop.webShop.entities.ChangePasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangePasswordTokenRepository extends JpaRepository<ChangePasswordToken, Integer> {

    @Query(value = "Select * from change_password_token where token = :token", nativeQuery = true)
    ChangePasswordToken findByToken(@Param("token") String token);
}
