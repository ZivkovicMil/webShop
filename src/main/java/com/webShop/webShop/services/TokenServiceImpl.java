package com.webShop.webShop.services;

import com.webShop.webShop.entities.ChangePasswordToken;
import com.webShop.webShop.entities.ConfirmationToken;
import com.webShop.webShop.repository.ChangePasswordTokenRepository;
import com.webShop.webShop.repository.ConfirmationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Value("${token.change_password}")
    private int changePasswordTokenExpiration;

    @Value("${token.registration}")
    private int registrationTokenExpiration;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private ChangePasswordTokenRepository changePasswordTokenRepository;

    @Override
    public boolean checkRegistrationTokenExpiration(ConfirmationToken token) {
        LocalDateTime expirationTime = token.getLocalDateTime().plusMinutes(registrationTokenExpiration);
        LocalDateTime currentTime = LocalDateTime.now();
        if (expirationTime.isBefore(currentTime)) {
            return false;
        } else if (expirationTime.isEqual(currentTime)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkChangePasswordTokenExpiration(ChangePasswordToken token) {
        LocalDateTime expirationTime = token.getLocalDateTime().plusMinutes(changePasswordTokenExpiration);
        LocalDateTime currentTime = LocalDateTime.now();
        if (expirationTime.isBefore(currentTime)) {
            return false;
        } else if (expirationTime.isEqual(currentTime)) {
            return false;
        }
        return true;
    }
}
