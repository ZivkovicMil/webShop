package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class ProductNotFoundException extends Exception{

    private final Messages message;

    public ProductNotFoundException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
