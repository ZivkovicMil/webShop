package com.webShop.webShop.services;

import com.webShop.webShop.entities.Order;
import com.webShop.webShop.entities.User;
import com.webShop.webShop.exceptions.EmailNotRegistered;
import com.webShop.webShop.exceptions.InvalidChangePasswordTokenException;
import com.webShop.webShop.exceptions.PasswordNotMatchException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.CommentDTO;
import com.webShop.webShop.models.UserDTO;
import com.webShop.webShop.models.UserRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User convertToUser(UserRegister userRegister);

    void turnOffUser(String email) throws UserNotFoundException;

    void turnOnUser(String email) throws UserNotFoundException;

    void sendChangePasswordEmail(String userEmail) throws EmailNotRegistered;

    void verifyAndChange(String token, String newPassword, String newPassword2) throws PasswordNotMatchException, InvalidChangePasswordTokenException;

    void changeAddress(String address, HttpServletRequest request);

    void changePhoneNumber(String phoneNumber, HttpServletRequest request);

    Page<UserDTO> findAll(Pageable pageable);

    List<CommentDTO> getAllComments() throws UserNotFoundException;
}
