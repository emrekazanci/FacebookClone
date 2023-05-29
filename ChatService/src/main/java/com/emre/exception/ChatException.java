package com.emre.exception;

import lombok.Getter;

@Getter
public class ChatException extends RuntimeException {
    private final ErrorType errorType;
    public ChatException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
    public ChatException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
}
