package com.itlang.util;

import com.itlang.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class UserCleanup {
    private final PersonService personService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteInactiveUsers(){
        personService.deleteInactive();
    }
}
