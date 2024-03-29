package com.itlang.models.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Task task;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    private Long correctAnswerId;

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);

        if (answer.isCorrect()){
            this.setCorrectAnswerId(answer.getId());
        }
        answers.add(answer);
    }
    public Answer getAnswer(){
        return answers.get(0);
    }
}
