package com.example.Ecommerce.Project.exeptionhandler.customexceptions;

public class CategoryAlreadyExists extends RuntimeException {

    public CategoryAlreadyExists(String message) {
                 super(message);
    }
}


