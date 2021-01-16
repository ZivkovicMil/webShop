package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class OrderNotFoundException extends Exception {

    private final Messages message;

    public OrderNotFoundException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
