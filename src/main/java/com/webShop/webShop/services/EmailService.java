package com.webShop.webShop.services;

import com.webShop.webShop.models.OrderDTO;

public interface EmailService {

    void sendRegistrationEmail(String email, String token);

    void sendChangePasswordEmail(String email, String token);

    void sendOrderDetailsEmail(String email, OrderDTO orderDTO);
}
