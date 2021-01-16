package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class InvalidChangePasswordTokenException extends Exception {

    private final Messages message;

    public InvalidChangePasswordTokenException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
