package com.itlang.services;

import com.itlang.models.Person;
import com.itlang.repositories.PeopleRepository;
//import com.itlang.repositories.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final PeopleRepository peopleRepository;

    public Long getUserId(String email){
        Person person = peopleRepository.findPersonByEmail(email);
        return person.getId();

    }
}
