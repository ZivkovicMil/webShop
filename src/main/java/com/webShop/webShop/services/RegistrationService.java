package com.webShop.webShop.services;

import com.webShop.webShop.exceptions.EmailException;
import com.webShop.webShop.exceptions.InvalidRegistrationTokenException;
import com.webShop.webShop.models.UserRegister;

public interface RegistrationService {

    void sendConfirmationLink(UserRegister userRegister) throws EmailException;

    void confirmUserToken(String token) throws InvalidRegistrationTokenException;
}
