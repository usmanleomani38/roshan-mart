package com.example.Ecommerce.Project.exeptionhandler.customexceptions;

public class ProductAlreadyExist extends RuntimeException  {
        public ProductAlreadyExist(String message) {
            super(message);
        }
    }

