package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class UserNotFoundException extends Exception {

    private final Messages message;

    public UserNotFoundException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
