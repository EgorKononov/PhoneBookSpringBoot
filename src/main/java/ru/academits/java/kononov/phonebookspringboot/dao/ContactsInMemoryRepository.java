package ru.academits.java.kononov.phonebookspringboot.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.academits.java.kononov.phonebookspringboot.dto.Contact;
import ru.academits.java.kononov.phonebookspringboot.exception.ValidationException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Slf4j
public class ContactsInMemoryRepository implements ContactsRepository {
    private static final Map<Integer, Contact> contacts = new ConcurrentHashMap<>();
    private static final AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public List<Contact> getContacts(String term) throws ValidationException {
        List<Contact> contactList;

        if (term == null || term.isBlank()) {
            synchronized (contacts) {
                contactList = new ArrayList<>(contacts.values());
            }
        } else {
            String termUpperCase = term.toUpperCase().trim();

            synchronized (contacts) {
                contactList = contacts.values().stream().filter(contact ->
                        contact.getFirstName().toUpperCase().contains(termUpperCase) ||
                                contact.getLastName().toUpperCase().contains(termUpperCase) ||
                                Long.toString(contact.getPhoneNumber()).contains(termUpperCase)
                ).toList();
            }
        }

        log.info("Contacts for term [{}] given {} ", term, contactList);

        return contactList;
    }

    @Override
    public void addContact(Contact contact) throws ValidationException {
        validateContact(contact);

        synchronized (contacts) {
            int contactId = currentId.getAndIncrement();

            Contact newContact = new Contact(
                    contactId,
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getPhoneNumber());

            contacts.put(contactId, newContact);

            log.info("New contact added: {}", newContact);
        }
    }

    private static void validateContact(Contact contact) throws ValidationException {
        if (contact == null) {
            throw new ValidationException("Contact cannot be null");
        }

        if (contact.getFirstName() == null || contact.getFirstName().trim().isEmpty()) {
            throw new ValidationException("Contact first name cannot be empty");
        }

        if (contact.getLastName() == null || contact.getLastName().trim().isEmpty()) {
            throw new ValidationException("Contact last name cannot be empty");
        }

        if (contact.getPhoneNumber() == null) {
            throw new ValidationException("Contact phone number cannot be empty");
        }
    }

    @Override
    public void deleteContact(int id) throws ValidationException {
        synchronized (contacts) {
            Contact removed = contacts.remove(id);
            log.info("Contact removed: {}", removed);

            if (removed == null) {
                throw new ValidationException("Contact with id=[" + id + "] not found");
            }
        }
    }

    @Override
    public void deleteRandomContact() throws ValidationException {
        synchronized (contacts) {
            if (!contacts.isEmpty()) {
                Set<Integer> keySet = contacts.keySet();
                keySet.stream().skip(new Random().nextInt(keySet.size()))
                        .findFirst()
                        .ifPresent(key -> {
                            Contact removed = contacts.remove(key);
                            log.info("Random contact deleted: {}", removed);
                        });
            } else {
                log.info("Unable to delete random contact: no contacts found");
            }
        }
    }
}
