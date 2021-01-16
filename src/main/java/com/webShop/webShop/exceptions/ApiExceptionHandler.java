package com.webShop.webShop.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.webShop.webShop.models.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidRegistrationTokenException.class)
    public ResponseEntity<ErrorDTO> handleException(InvalidRegistrationTokenException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ErrorDTO> handleException(EmailException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotRegistered.class)
    public ResponseEntity<ErrorDTO> handleException(EmailNotRegistered e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorDTO> handleException(PasswordNotMatchException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidChangePasswordTokenException.class)
    public ResponseEntity<ErrorDTO> handleException(InvalidChangePasswordTokenException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorDTO> handleException(TokenExpiredException e) {
        String errorCode = e.getMessage();
        String errorMessage = e.getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductQuantityException.class)
    public ResponseEntity<ErrorDTO> productQuantityHandler(ProductQuantityException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<ErrorDTO> handleException(RoleException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(ProductNotFoundException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(UserNotFoundException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(OrderNotFoundException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyOrderException.class)
    public ResponseEntity<ErrorDTO> handleException(EmptyOrderException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageNotExistException.class)
    public ResponseEntity<ErrorDTO> handleException(ImageNotExistException e) {
        String errorCode = e.getMessages().getIdentification();
        String errorMessage = e.getMessages().getMessage();
        return new ResponseEntity<>(new ErrorDTO(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
    }

}
