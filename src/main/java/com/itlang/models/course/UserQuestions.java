package com.itlang.models.course;

import com.itlang.models.Person;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

@Entity
@Data
public class UserQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Question question;

    @OneToOne
    @JoinColumn(name = "person_id")
    @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
    private Person person;

    private Long levelId;
    private String courseTitle;
    private Long lastAnswer;
    private boolean isCorrect;
}