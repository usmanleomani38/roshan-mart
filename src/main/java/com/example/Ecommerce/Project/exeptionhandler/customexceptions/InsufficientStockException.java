package com.example.Ecommerce.Project.exeptionhandler.customexceptions;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}