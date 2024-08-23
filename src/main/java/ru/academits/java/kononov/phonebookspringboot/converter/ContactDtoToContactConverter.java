package ru.academits.java.kononov.phonebookspringboot.converter;

import org.springframework.stereotype.Service;
import ru.academits.java.kononov.phonebookspringboot.dto.Contact;
import ru.academits.java.kononov.phonebookspringboot.dto.ContactDto;

@Service
public class ContactDtoToContactConverter implements Converter<ContactDto, Contact> {
    @Override
    public Contact convert(ContactDto source) {
        return new Contact(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPhoneNumber()
        );
    }
}
