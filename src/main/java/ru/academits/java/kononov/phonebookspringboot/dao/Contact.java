package ru.academits.java.kononov.phonebookspringboot.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
}
