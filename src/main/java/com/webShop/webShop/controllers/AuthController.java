package com.webShop.webShop.controllers;

import com.webShop.webShop.config.UserPrincipalDetailsService;
import com.webShop.webShop.exceptions.*;
import com.webShop.webShop.models.UserRegister;
import com.webShop.webShop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody UserRegister userRegister) throws EmailException {
        registrationService.sendConfirmationLink(userRegister);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{registrationToken}", method = RequestMethod.POST)
    public ResponseEntity confirmUser(@PathVariable("registrationToken") String token) throws InvalidRegistrationTokenException {
        registrationService.confirmUserToken(token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity forgotPassword(@RequestParam(name = "userEmail", required = true) String userEmail) throws EmailNotRegistered {
        userService.sendChangePasswordEmail(userEmail);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/forgotPassword/{token}", method = RequestMethod.POST)
    public ResponseEntity changePassword(@PathVariable("token") String token, @RequestParam(name = "newPassword", required = true) String newPassword, @RequestParam(name = "newPassword2", required = true) String newPassword2) throws PasswordNotMatchException, InvalidChangePasswordTokenException {
        userService.verifyAndChange(token, newPassword, newPassword2);
        return new ResponseEntity(HttpStatus.OK);
    }
}
