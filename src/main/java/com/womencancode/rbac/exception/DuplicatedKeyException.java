package com.womencancode.rbac.exception;

public class DuplicatedKeyException extends ServiceException {
    public DuplicatedKeyException(String message) {
        super(message);
    }

    public DuplicatedKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
