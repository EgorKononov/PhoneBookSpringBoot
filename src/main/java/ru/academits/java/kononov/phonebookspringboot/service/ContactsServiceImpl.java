package ru.academits.java.kononov.phonebookspringboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.academits.java.kononov.phonebookspringboot.dao.ContactsRepository;
import ru.academits.java.kononov.phonebookspringboot.dao.Contact;
import ru.academits.java.kononov.phonebookspringboot.exception.ValidationException;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
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

    @Override
    public void deleteRandomContact() throws ValidationException {
        List<Contact> contacts = contactsRepository.getContacts(null);

        if (!contacts.isEmpty()) {
            Contact randomContact = contacts.get(new Random().nextInt(contacts.size()));
            contactsRepository.deleteContact(randomContact.getId());
            log.info("Random contact has been deleted: {}", randomContact);
        } else {
            log.info("Unable to delete random contact: no contacts found");
        }
    }
}
