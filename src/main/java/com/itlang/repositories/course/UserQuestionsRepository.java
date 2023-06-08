package com.itlang.repositories.course;

import com.itlang.models.Person;
import com.itlang.models.course.Question;
import com.itlang.models.course.UserQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionsRepository extends JpaRepository<UserQuestions, Long> {
    UserQuestions findUserQuestionsByQuestionAndPerson (Question question, Person person);
    List<UserQuestions> findUserQuestionsByPersonAndLevelId (Person person, Long id);
    List<UserQuestions> findUserQuestionsByQuestion(Question question);
    void deleteAllById(Long id);
}
