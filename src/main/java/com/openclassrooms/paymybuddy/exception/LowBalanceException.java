package com.openclassrooms.paymybuddy.exception;

public class LowBalanceException extends Exception {
    public LowBalanceException(String errorMessage){
        super(errorMessage);
    }
}
