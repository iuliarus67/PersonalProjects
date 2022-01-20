package com.example.proiectse.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String title) {
        super("The book with title : " + title + " cannot be found");
    }
}
