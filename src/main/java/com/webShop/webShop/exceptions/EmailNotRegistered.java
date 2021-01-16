package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class EmailNotRegistered extends Exception {

    private final Messages message;

    public EmailNotRegistered(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
