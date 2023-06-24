package com.examen.truper.truper.common.exception;

import lombok.Getter;

@Getter
public class TruperPurchaseException extends RuntimeException{
    private final int errorCode;
    private final String errorMessage;

    public TruperPurchaseException(int errorCode,
                                   String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
