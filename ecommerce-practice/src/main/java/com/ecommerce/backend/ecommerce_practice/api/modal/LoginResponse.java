package com.ecommerce.backend.ecommerce_practice.api.modal;

public class LoginResponse {
    private String JWT;
    private boolean success;
    private String failureReason;
    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getJWT() {
        return JWT;
    }

}
