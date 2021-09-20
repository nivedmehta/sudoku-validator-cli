package com.game;

public enum ValidationErrorType {
    INVALID_SIZE(10),
    INVALID_VALUES(11),
    INVALID_THREE_BY_THREE_VALUES(12);

    private int errorCode ;

    ValidationErrorType(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
