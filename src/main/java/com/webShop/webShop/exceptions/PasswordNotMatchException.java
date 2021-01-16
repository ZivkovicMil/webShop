package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class PasswordNotMatchException extends Exception {

    private final Messages message;

    public PasswordNotMatchException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
