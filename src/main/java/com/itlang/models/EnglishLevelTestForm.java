package com.itlang.models;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnglishLevelTestForm {

    private List<EnglishLevelTestQuestion> questionList;

    public List<EnglishLevelTestQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<EnglishLevelTestQuestion> questionList) {
        this.questionList = questionList;
    }
}
