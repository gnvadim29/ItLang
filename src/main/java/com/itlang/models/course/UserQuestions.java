package com.itlang.models.course;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long questionId;
    private Long personId;

    private Long levelId;
    private String courseTitle;
    private Long lastAnswer;
    private boolean isCorrect;
}