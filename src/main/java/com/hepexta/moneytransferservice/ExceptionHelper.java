package com.hepexta.moneytransferservice;

public class ExceptionHelper {
    public static void throwIf(boolean condition, String message) {
        if (condition){
            throw new RuntimeException(message);
        }
    }
}
