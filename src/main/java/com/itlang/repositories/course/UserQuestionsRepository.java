package com.itlang.repositories.course;

import com.itlang.models.Person;
import com.itlang.models.course.Question;
import com.itlang.models.course.UserQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionsRepository extends JpaRepository<UserQuestions, Long> {
    UserQuestions findUserQuestionsByQuestionIdAndPersonId (Long questionId, Long personId);
    List<UserQuestions> findUserQuestionsByPersonIdAndLevelId (Long personId, Long id);
    List<UserQuestions> findUserQuestionsByQuestionId(Long id);
    void deleteAllById(Long id);
}
