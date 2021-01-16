package com.webShop.webShop.services.schedulerServices;

import com.webShop.webShop.entities.ChangePasswordToken;
import com.webShop.webShop.entities.ConfirmationToken;
import com.webShop.webShop.repository.ChangePasswordTokenRepository;
import com.webShop.webShop.repository.ConfirmationTokenRepository;
import com.webShop.webShop.repository.UserRepository;
import com.webShop.webShop.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenSchedulerServiceImpl implements TokenSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(TokenSchedulerServiceImpl.class);

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private ChangePasswordTokenRepository changePasswordTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;


    @Override
    public void deleteRegistrationTokens() {
        List<ConfirmationToken> allTokens = confirmationTokenRepository.findAllTokens();
        if (allTokens != null && !allTokens.isEmpty()) {
            for (ConfirmationToken token : allTokens) {
                if (!tokenService.checkRegistrationTokenExpiration(token)) {
                    confirmationTokenRepository.delete(token);
                    userRepository.delete(token.getUser());
                    log.info("Registration token for user: " + token.getUser().getEmail() + " is deleted!");
                }
            }
        }
    }

    @Override
    public void deleteChangePasswordTokens() {
        List<ChangePasswordToken> allTokens = changePasswordTokenRepository.findAll();
        if (allTokens != null || !allTokens.isEmpty()) {
            for (ChangePasswordToken token : allTokens) {
                if (!tokenService.checkChangePasswordTokenExpiration(token)) {
                    changePasswordTokenRepository.delete(token);
                    log.info("Change password token for user: " + token.getUser().getEmail() + " is deleted!");
                }
            }
        }
    }
}
