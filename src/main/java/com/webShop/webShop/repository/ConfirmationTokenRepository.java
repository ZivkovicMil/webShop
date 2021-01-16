package com.webShop.webShop.repository;

import com.webShop.webShop.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    @Query(value = "Select * from confirmation_token where token  = :token", nativeQuery = true)
    ConfirmationToken findByToken(@Param("token") String token);

    @Query(value = "select * from confirmation_token", nativeQuery = true)
    List<ConfirmationToken> findAllTokens();

}
