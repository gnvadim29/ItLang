package com.itlang.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnglishLevelTestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    @Column(columnDefinition = "text")
    private String questionTitle;
    private String questionLevel;
    @NotEmpty(message = "Answer 1 should not be empty")
    private String questionAnswer1;
    @NotEmpty(message = "Answer 1 should not be empty")
    private String questionAnswer2;
    @NotEmpty(message = "Answer 1 should not be empty")
    private String questionAnswer3;
    private String questionAnswer4;
    private int questionCorrectAnswer;
    private int user_answer = -1;
}
