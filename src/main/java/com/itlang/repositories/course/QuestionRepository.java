package com.itlang.repositories.course;

import com.itlang.models.course.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository  extends JpaRepository<Question, Long> {
    List<Question> findQuestionsByTaskId(Long id);
}
