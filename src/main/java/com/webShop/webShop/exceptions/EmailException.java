package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class EmailException extends Exception {

    private final Messages message;

    public EmailException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
