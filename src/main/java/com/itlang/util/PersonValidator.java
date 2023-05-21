package com.itlang.util;

import com.itlang.models.Person;
import com.itlang.repositories.PeopleRepository;
import com.itlang.services.PersonDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * @author Vadym Hnatiuk
 */
@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    private final PeopleRepository personRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Optional<Person> person = personRepository.findByEmail(((Person) o).getEmail());

        if (!person.isEmpty()){
            errors.rejectValue("email", "", "Користувач з таким e-mail вже існує");
        }

    }
}
