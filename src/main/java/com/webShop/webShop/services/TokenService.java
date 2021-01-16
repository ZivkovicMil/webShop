package com.webShop.webShop.services;

import com.webShop.webShop.entities.ChangePasswordToken;
import com.webShop.webShop.entities.ConfirmationToken;

public interface TokenService {

    boolean checkRegistrationTokenExpiration(ConfirmationToken token);

    boolean checkChangePasswordTokenExpiration(ChangePasswordToken token);
}
