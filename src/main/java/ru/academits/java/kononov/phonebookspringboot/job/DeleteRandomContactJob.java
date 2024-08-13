package ru.academits.java.kononov.phonebookspringboot.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.academits.java.kononov.phonebookspringboot.dao.ContactsRepository;
import ru.academits.java.kononov.phonebookspringboot.exception.ValidationException;

@Component
@EnableScheduling
@Slf4j
public class DeleteRandomContactJob {
    private final ContactsRepository contactsRepository;

    public DeleteRandomContactJob(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @Scheduled(initialDelay = 10000, fixedRate = 10000)
    public void execute() {
        try {
            log.info("DeleteRandomContactJob start");
            contactsRepository.deleteRandomContact();
            log.info("DeleteRandomContactJob finish");
        } catch (ValidationException e) {
            log.error("ValidationException: {}", e.getMessage(), e);
        }
    }
}
