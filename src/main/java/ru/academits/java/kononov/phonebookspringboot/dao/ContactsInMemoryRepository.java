package ru.academits.java.kononov.phonebookspringboot.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
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
    public List<Contact> getContacts(String term) {
        List<Contact> contactsList;

        if (term == null || term.isBlank()) {
            synchronized (contacts) {
                contactsList = new ArrayList<>(contacts.values());
            }
        } else {
            String termUpperCaseTrim = term.toUpperCase().trim();

            synchronized (contacts) {
                contactsList = contacts.values().stream().filter(contact ->
                        contact.getFirstName().toUpperCase().contains(termUpperCaseTrim) ||
                                contact.getLastName().toUpperCase().contains(termUpperCaseTrim) ||
                                contact.getPhoneNumber().contains(termUpperCaseTrim)
                ).toList();
            }
        }

        log.info("Contacts for term [{}] given {} ", term, contactsList);

        return contactsList;
    }

    @Override
    public void addContact(Contact contact) throws ValidationException {
        validateContact(contact);

        synchronized (contacts) {
            int contactId = currentId.getAndIncrement();

            Contact newContact = new Contact(
                    contactId,
                    contact.getFirstName().trim(),
                    contact.getLastName().trim(),
                    contact.getPhoneNumber().trim());

            contacts.put(contactId, newContact);

            log.info("New contact added: {}", newContact);
        }
    }

    private static void validateContact(Contact contact) throws ValidationException {
        if (contact == null) {
            throw new ValidationException("Contact cannot be null");
        }

        validateForEmpty(contact.getFirstName(), "first name");
        validateForEmpty(contact.getLastName(), "last name");
        validatePhoneNumberForEmptyAndUnique(contact.getPhoneNumber());
    }

    private static void validateForEmpty(String fieldName, String fieldValue) throws ValidationException {
        if (fieldValue == null || fieldValue.isBlank()) {
            throw new ValidationException("Contact " + fieldName + " cannot be empty");
        }
    }

    private static void validatePhoneNumberForEmptyAndUnique(String phoneNumber) throws ValidationException {
        validateForEmpty(phoneNumber, "phone number");
        String phoneNumberTrim = phoneNumber.trim();

        synchronized (contacts) {
            Optional<Contact> contactWithSamePhoneNumber = contacts.values().stream()
                    .filter(currentContact -> Objects.equals(currentContact.getPhoneNumber(), phoneNumberTrim))
                    .findFirst();

            if (contactWithSamePhoneNumber.isPresent()) {
                throw new ValidationException("Contact with phone number [" + phoneNumberTrim + "] already exists");
            }
        }
    }

    @Override
    public void deleteContact(int id) throws ValidationException {
        synchronized (contacts) {
            Contact removedContact = contacts.remove(id);
            log.info("Contact has been deleted: {}", removedContact);

            if (removedContact == null) {
                throw new ValidationException("Contact with id=[" + id + "] not found");
            }
        }
    }
}
