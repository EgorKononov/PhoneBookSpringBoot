package ru.academits.java.kononov.phonebookspringboot.exceptions;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
