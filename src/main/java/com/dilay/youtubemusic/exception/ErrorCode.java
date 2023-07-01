package com.dilay.youtubemusic.exception;

public enum ErrorCode {

    unknown(400),
    validation(422),
    unauthorized(401),
    forbidden(403),
    resource_missing(404),
    account_already_exists(409),
    account_missing(404),
    password_mismatch(409),
    account_already_verified(403),
    code_expired(410),
    code_mismatch(409),
    already_onboarded(409),
    insufficient_balance(409),
    conflict(409),
    already_submitted(409);

    private final int httpCode;

    ErrorCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
