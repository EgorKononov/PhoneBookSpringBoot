package ru.academits.java.kononov.phonebookspringboot.service;

import org.springframework.stereotype.Service;
import ru.academits.java.kononov.phonebookspringboot.dto.Contact;
import ru.academits.java.kononov.phonebookspringboot.dao.ContactsRepository;
import ru.academits.java.kononov.phonebookspringboot.exception.ValidationException;

import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {
    private final ContactsRepository contactsRepository;

    public ContactsServiceImpl(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @Override
    public List<Contact> getContacts(String term) throws ValidationException {
        return contactsRepository.getContacts(term);
    }

    @Override
    public void addContact(Contact contact) throws ValidationException {
        contactsRepository.addContact(contact);
    }

    @Override
    public void deleteContact(int id) throws ValidationException {
        contactsRepository.deleteContact(id);
    }
}
