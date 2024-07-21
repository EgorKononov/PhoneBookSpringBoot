package ru.academits.java.kononov.phonebookspringboot.dao;

import org.springframework.stereotype.Repository;
import ru.academits.java.kononov.phonebookspringboot.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ContactsInMemoryRepository implements ContactsRepository {
    private static final Map<Integer, Contact> contacts = new ConcurrentHashMap<>();
    private static final AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public List<Contact> getContacts(String term) throws ValidationException {
        if (term == null || term.trim().isEmpty()) {
            synchronized (contacts) {
                return new ArrayList<>(contacts.values());
            }
        } else {
            String termUpperCase = term.toUpperCase();

            synchronized (contacts) {
                return contacts.values().stream().filter(contact ->
                        contact.getFirstName().toUpperCase().contains(termUpperCase) ||
                                contact.getLastName().toUpperCase().contains(termUpperCase) ||
                                Long.toString(contact.getPhoneNumber()).contains(termUpperCase)
                ).toList();
            }
        }
    }

    @Override
    public void addContact(Contact contact) throws ValidationException {
        validateContact(contact);

        synchronized (contacts) {
            int contactId = currentId.getAndIncrement();

            contacts.put(contactId,
                    new Contact(
                            contactId,
                            contact.getFirstName(),
                            contact.getLastName(),
                            contact.getPhoneNumber()));
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
            if (contacts.remove(id) == null) {
                throw new ValidationException("Contact not found");
            }
        }
    }
}
