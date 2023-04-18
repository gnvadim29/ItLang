package com.itlang.services;

import com.itlang.models.Person;
import com.itlang.repositories.PeopleRepository;
import com.itlang.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Vadym Hnatiuk
 */
@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByEmail(email);

        if(person.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new PersonDetails(person.get());
    }
}
