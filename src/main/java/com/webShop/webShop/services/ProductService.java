package com.webShop.webShop.services;

import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.ProductQuantityException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.OrderDTO;
import com.webShop.webShop.models.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ProductDTO> getNewProducts(Pageable pageable);

    void addNewProduct(ProductDTO product) throws IllegalArgumentException;

    OrderDTO addToCart(Integer product_id, Integer quantity) throws ProductQuantityException, UserNotFoundException, ProductNotFoundException;

    void addQuantity(Integer product_id, Integer quantity) throws ProductNotFoundException;

    List<String> allCategories();

    Page<ProductDTO> getByCategory(String category, Pageable pageable);

    ProductDTO getProduct(int product_id) throws ProductNotFoundException;

    void addComment(Integer product_id, String comment) throws ProductNotFoundException, UserNotFoundException;
}
