package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class EmptyOrderException extends Exception{

    private final Messages message;

    public EmptyOrderException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
