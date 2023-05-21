package com.itlang.models.course;

import com.itlang.models.Image;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class QuestionBody {
    private String title;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    private String correctAnswer;
    private String questionText;


}
