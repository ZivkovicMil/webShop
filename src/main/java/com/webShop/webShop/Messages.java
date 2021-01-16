package com.webShop.webShop;

import java.util.ResourceBundle;

public enum Messages {

    INVALID_USERNAME_OR_PASSWORD("9001", "error.invalid_username_or_password"),
    EMAIL_IS_ALREADY_TAKEN("9002", "error.used_email"),
    INVALID_REGISTRATION_TOKEN("9003", "error.invalid_registration_token"),
    EMAIL_NOT_REGISTERED("9004", "error.email_doesnt_exist"),
    PASSWORD_DONT_MATCH("9005", "error.password_dont_match"),
    CHANGE_PASSWORD_TOKEN_DOESNT_EXIST("9006", "error.invalid_change_password_token"),
    CHANGE_PASSWORD_TOKEN_IS_EXPIRED("9007", "error.change_password_token_expired"),
    REGISTRATION_TOKEN_IS_EXPIRED("9008", "error.registration_token_expired"),
    PRODUCT_OUT_OF_STOCK("9009", "error.product_quantity"),
    QUANTITY_EXCEPTION("9017", "error.quantity_is_not_positive"),
    EMAIL_DOESNT_EXIST_IN_DATABSE("9010", "error.email_not_found"),
    ROLE_DOESNT_EXIST("9011", "error.role_not_found"),
    PRODUCT_NOT_FOUND("9012", "error.product_not_found"),
    ORDER_NOT_FOUND("9013", "error.order_not_found"),
    USER_NOT_FOUND("9014", "error.user_not_found"),
    IMAGE_NOT_FOUND("9015", "error.image_not_found"),
    EMPTY_ORDER("9016", "error.order_is_empty");

    private static final ResourceBundle errorMessageBundle = ResourceBundle.getBundle("messages");

    private final String identification;
    private final String messagePropertyKey;
    private final Object[] formatArguments;

    Messages(String identification, String messagePropertyKey, Object... formatArgs) {
        this.identification = identification;
        this.messagePropertyKey = messagePropertyKey;
        this.formatArguments = formatArgs;
    }

    public String getIdentification() {
        return identification;
    }

    public String getMessage() {
        return String.format(errorMessageBundle.getString(messagePropertyKey), formatArguments);
    }
}
