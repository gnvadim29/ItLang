package com.itlang.services.course;

import com.itlang.models.course.Answer;
import com.itlang.models.course.Question;
import com.itlang.models.course.QuestionBody;
import com.itlang.models.course.Task;
import com.itlang.repositories.course.QuestionRepository;
import com.itlang.repositories.course.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TaskRepository taskRepository;

    public void addQuestion(Long id, QuestionBody questionBody) {
        Task task = taskRepository.findTaskById(id);
        Question question = new Question();
        Answer answer1 = new Answer();
        Answer answer2 = new Answer();
        Answer answer3 = new Answer();

        question.setTitle(questionBody.getTitle());

        answer1.setTitle(questionBody.getAnswer1());
        if(questionBody.getCorrectAnswer() == 0){
            answer1.setCorrect(true);
        }
        question.addAnswer(answer1);

        answer2.setTitle(questionBody.getAnswer2());
        if(questionBody.getCorrectAnswer() == 1){
            answer1.setCorrect(true);
        }
        question.addAnswer(answer2);

        answer3.setTitle(questionBody.getAnswer3());
        if(questionBody.getCorrectAnswer() == 2){
            answer3.setCorrect(true);
        }
        question.addAnswer(answer3);

        task.addQuestion(question);
        taskRepository.save(task);
    }

    public List<Question> getQuestions(Long id) {
        return questionRepository.findQuestionsByTaskId(id);
    }
}
