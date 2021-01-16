package com.webShop.webShop.services;

import com.webShop.webShop.Messages;
import com.webShop.webShop.controllers.AuthController;
import com.webShop.webShop.entities.ConfirmationToken;
import com.webShop.webShop.entities.User;
import com.webShop.webShop.exceptions.EmailException;
import com.webShop.webShop.exceptions.InvalidRegistrationTokenException;
import com.webShop.webShop.models.UserRegister;
import com.webShop.webShop.repository.ConfirmationTokenRepository;
import com.webShop.webShop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public void sendConfirmationLink(UserRegister userRegister) throws EmailException {

        if (userRepository.findByEmail(userRegister.getEmail()) != null) {
            throw new EmailException(Messages.EMAIL_IS_ALREADY_TAKEN);
        }
        User user = userService.convertToUser(userRegister);
        String userEmail = user.getEmail();
        String token = generateToken();
        emailService.sendRegistrationEmail(userEmail, token);
        log.info("Mail is sent");
        userRepository.save(user);
        log.info("User with email:" + user.getEmail() + "is saved into database!");
        confirmationTokenRepository.save(new ConfirmationToken(token, user));
        log.info("Email token is saved into database");
    }

    @Override
    public void confirmUserToken(String token) throws InvalidRegistrationTokenException {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        try {
            if (confirmationToken != null) {
                if (tokenService.checkRegistrationTokenExpiration(confirmationToken)) {
                    User user = confirmationToken.getUser();
                    user.setIs_enabled(true);
                    userRepository.save(user);
                    confirmationTokenRepository.deleteById(confirmationToken.getId());
                    log.info("Register token for user " + user.getEmail() + " is deleted from database");
                } else {
                    throw new InvalidRegistrationTokenException(Messages.REGISTRATION_TOKEN_IS_EXPIRED);
                }
            } else {
                throw new InvalidRegistrationTokenException(Messages.INVALID_REGISTRATION_TOKEN);
            }
        } catch (InvalidRegistrationTokenException e) {
            log.info("Invalid registration token");
            throw (e.getMessages().equals(Messages.REGISTRATION_TOKEN_IS_EXPIRED) ? new InvalidRegistrationTokenException(Messages.REGISTRATION_TOKEN_IS_EXPIRED) : new InvalidRegistrationTokenException(Messages.INVALID_REGISTRATION_TOKEN));
        }
    }

    private String generateToken() {
        String registrationToken = UUID.randomUUID().toString();
        log.info("Token is generated" + registrationToken);
        return registrationToken;
    }
}
