package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class InvalidRegistrationTokenException extends Exception {

    private final Messages message;

    public InvalidRegistrationTokenException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
