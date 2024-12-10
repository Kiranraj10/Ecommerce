package com.ecommerce.backend.ecommerce_practice.exception;

public class UserNotVerified extends Exception{
    private boolean emailSent;

    public UserNotVerified(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public boolean isEmailSent() {
        return emailSent;
    }
}
