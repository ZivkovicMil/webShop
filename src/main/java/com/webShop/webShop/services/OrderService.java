package com.webShop.webShop.services;

import com.webShop.webShop.exceptions.EmptyOrderException;
import com.webShop.webShop.exceptions.OrderNotFoundException;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.OrderDTO;

import java.util.List;

public interface OrderService {

    void confirmOrder(OrderDTO orderDTO) throws OrderNotFoundException;

    OrderDTO checkOrder(int id) throws OrderNotFoundException;

    OrderDTO removeProductFromOrder(int product_id) throws UserNotFoundException, ProductNotFoundException, EmptyOrderException;

    List<OrderDTO> allOrders();


}
