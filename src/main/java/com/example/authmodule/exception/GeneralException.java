package com.example.authmodule.exception;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 10:30
 */
public class GeneralException extends Exception{
    private String message;

    public GeneralException(String message) {
        super(message);
        this.message = message;
    }
}
