package ru.academits.java.kononov.phonebookspringboot.dao;

import ru.academits.java.kononov.phonebookspringboot.exception.ValidationException;

import java.util.List;

public interface ContactsRepository {
    List<Contact> getContacts(String term) throws ValidationException;

    void addContact(Contact contact) throws ValidationException;

    void deleteContact(int id) throws ValidationException;
}
