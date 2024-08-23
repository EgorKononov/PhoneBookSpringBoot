package ru.academits.java.kononov.phonebookspringboot.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.academits.java.kononov.phonebookspringboot.exception.ValidationException;
import ru.academits.java.kononov.phonebookspringboot.service.ContactsService;

@Component
//@EnableScheduling
@Slf4j
public class DeleteRandomContactJob {
    private final ContactsService contactsService;

    public DeleteRandomContactJob(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @Scheduled(initialDelay = 10000, fixedRate = 10000)
    public void execute() {
        try {
            log.info("DeleteRandomContactJob start");
            contactsService.deleteRandomContact();
            log.info("DeleteRandomContactJob finish");
        } catch (ValidationException e) {
            log.error("ValidationException: {}", e.getMessage(), e);
        }
    }
}
