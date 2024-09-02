package ru.academits.java.kononov.phonebookspringboot.converter;

import org.springframework.stereotype.Service;
import ru.academits.java.kononov.phonebookspringboot.dao.Contact;
import ru.academits.java.kononov.phonebookspringboot.dto.ContactDto;

@Service
public class ContactToContactDtoConverter implements Converter<Contact, ContactDto> {
    @Override
    public ContactDto convert(Contact source) {
        return new ContactDto(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPhoneNumber()
        );
    }
}
