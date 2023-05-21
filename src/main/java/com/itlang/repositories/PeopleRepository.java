package com.itlang.repositories;

import com.itlang.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Vadym Hnatiuk
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
    Person findPersonByEmail(String email);
    @Query("SELECT u FROM Person u WHERE u.verificationCode = ?1")
    public Person findByVerificationCode(String code);

}
