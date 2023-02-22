package com.apartments.base.utils.models;

public enum ErrorType {
    BAD_REQUEST(400),
    NOT_FOUND(404);

    public final int code;

    private ErrorType(int code) {
        this.code = code;
    }


}
