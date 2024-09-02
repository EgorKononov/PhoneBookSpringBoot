package ru.academits.java.kononov.phonebookspringboot.service;

import ru.academits.java.kononov.phonebookspringboot.dao.Contact;
import ru.academits.java.kononov.phonebookspringboot.exception.ValidationException;

import java.util.List;

public interface ContactsService {
    List<Contact> getContacts(String term) throws ValidationException;

    void addContact(Contact contact) throws ValidationException;

    void deleteContact(int id) throws ValidationException;

    void deleteRandomContact() throws ValidationException;
}
