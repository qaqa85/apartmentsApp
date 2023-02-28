package com.apartments.base.utils.model;

public enum ErrorType {
    BAD_REQUEST(400),
    NOT_FOUND(404);

    public final int code;

    ErrorType(int code) {
        this.code = code;
    }


}
