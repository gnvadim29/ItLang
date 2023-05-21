package com.itlang.repositories;

import com.itlang.models.EnglishLevelTestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnglishLevelTestRepository extends JpaRepository<EnglishLevelTestQuestion, Long> {
    public List<EnglishLevelTestQuestion> findAllByOrderByQuestionLevel();
    public  EnglishLevelTestQuestion findEnglishLevelTestQuestionById(Long id);

    @Query("select que FROM EnglishLevelTestQuestion que WHERE que.questionLevel = 'A1' ORDER BY rand() limit 5")
    List<EnglishLevelTestQuestion> findRandomQuestionsA1();

    @Query("select que FROM EnglishLevelTestQuestion que WHERE que.questionLevel = 'A2' ORDER BY rand() limit 5")
    List<EnglishLevelTestQuestion> findRandomQuestionsA2();

    @Query("select que FROM EnglishLevelTestQuestion que WHERE que.questionLevel = 'B1' ORDER BY rand() limit 5")
    List<EnglishLevelTestQuestion> findRandomQuestionsB1();

    @Query("select que FROM EnglishLevelTestQuestion que WHERE que.questionLevel = 'B2' ORDER BY rand() limit 5")
    List<EnglishLevelTestQuestion> findRandomQuestionsB2();

    @Query("select que FROM EnglishLevelTestQuestion que WHERE que.questionLevel = 'C' ORDER BY rand() limit 5")
    List<EnglishLevelTestQuestion> findRandomQuestionsC();
}
