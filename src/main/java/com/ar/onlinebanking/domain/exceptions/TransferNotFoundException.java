package com.ar.onlinebanking.domain.exceptions;

public class TransferNotFoundException extends RuntimeException{

    public TransferNotFoundException(String message){
        super(message);
    }
}
