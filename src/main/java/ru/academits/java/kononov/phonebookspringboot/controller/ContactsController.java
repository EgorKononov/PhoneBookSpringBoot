package ru.academits.java.kononov.phonebookspringboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.academits.java.kononov.phonebookspringboot.dto.Contact;
import ru.academits.java.kononov.phonebookspringboot.exception.ValidationException;
import ru.academits.java.kononov.phonebookspringboot.service.ContactsService;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {
    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @GetMapping
    public List<Contact> getContacts(@RequestParam(required = false) String term) {
        try {
            return contactsService.getContacts(term);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping
    public void addContact(@RequestBody Contact contact) {
        try {
            contactsService.addContact(contact);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping
    public void deleteContact(@RequestParam int id) {
        try {
            contactsService.deleteContact(id);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
