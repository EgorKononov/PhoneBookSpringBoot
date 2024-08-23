package ru.academits.java.kononov.phonebookspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDto {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
