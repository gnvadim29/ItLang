package com.itlang.repositories;

import com.itlang.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Vadym Hnatiuk
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
    Person findPersonByEmail(String email);
    @Query("SELECT u FROM Person u WHERE u.verificationCode = ?1")
    Person findByVerificationCode(String code);
    Person findPersonById (Long id);
    @Query(value = "SELECT * FROM person WHERE last_activity < DATE_SUB(NOW(), INTERVAL 12 MONTH)", nativeQuery = true)
    List<Person> findInactive();
    @Query(value = "SELECT * FROM person WHERE enabled = false", nativeQuery = true)
    List<Person> findDisabled();
    Person findPersonByResetToken(String token);

}
