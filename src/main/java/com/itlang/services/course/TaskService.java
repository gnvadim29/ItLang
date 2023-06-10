package com.itlang.services.course;

import com.itlang.models.course.*;
import com.itlang.repositories.BlogPostImageRepository;
import com.itlang.repositories.course.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final LevelRepository levelRepository;
    private final CourseRepository courseRepository;
    private final UserQuestionsRepository userQuestionsRepository;
    private final QuestionRepository questionRepository;
    private final BlogPostImageRepository imageRepository;
    private final AnswerRepository answerRepository;


    public void createTask(Long id, String value, String title) {
        Task task = new Task();
        task.setTitle(title);
        if(value.equals("listening_text_more_answer") || value.equals("listening_true_false")
                || value.equals("listening_text_answer") || value.equals("listening_image_answer")){
            task.setType("listening");
        } else if(value.equals("reading_text_answers") || value.equals("reading_texts_answer")){
            task.setType("reading");
        } else if(value.equals("use_text_paste") || value.equals("use_text_answers")){
            task.setType("useOfEnglish");
        } else if(value.equals("writing")){
            task.setType("writing");
        }

        task.setDescription(value);
        Level level = levelRepository.findLevelById(id);
        level.addTask(task);
        levelRepository.save(level);
    }
    public List<Task> getListeningTasks(Long id){
        return taskRepository.findAllByTypeAndLevel_Id("listening", id);
    }
    public Task getTaskById(Long id){
        return  taskRepository.findTaskById(id);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findTaskById(id);
        List<Question> questions = task.getQuestions();

        for (int i = 0; i < questions.size(); i++){
            List<Answer> answers = questionRepository.findQuestionById(questions.get(i).getId()).getAnswers();
            if (answers.size() != 0){
                if(answers.get(0).getImage()!=null){
                    for (Answer answer : answers) {
                        imageRepository.deleteImageById(answer.getImage().getId());
                        answerRepository.deleteById(answer.getId());
                    }
                }
            }



            List<UserQuestions> userQuestions = userQuestionsRepository.findUserQuestionsByQuestionId(questions.get(i).getId());
            if (userQuestions.size() != 0){
                for (int j = 0; j < userQuestions.size(); j++){
                    if (userQuestions.get(j) != null){
                        userQuestionsRepository.delete(userQuestions.get(j));
                    }
                }
            }
        }


        taskRepository.deleteById(id);
    }

    public Long saveTask(Long id, Task task) {
        Task task1 = taskRepository.findTaskById(id);
        task1.setTitle(task.getTitle());
        task1.setMediaUrl(task.getMediaUrl());
        task1.setTextDescription(task.getTextDescription());
        task1.setText(task.getText());

        taskRepository.save(task1);
        return task1.getLevel().getId();
    }

    public Object getReadingTasks(Long id) {
        return taskRepository.findAllByTypeAndLevel_Id("reading", id);
    }

    public List<Task> getListeningTasksByLevelId(Long id) {
        return taskRepository.findTaskByLevelIdAndType(id, "listening");
    }



    public List<Task> getReadingTasksByLevelId(Long id) {
        return taskRepository.findTaskByLevelIdAndType(id, "reading");
    }

    public List<Task> getUseOfEnglish(Long id) {
        return taskRepository.findAllByTypeAndLevel_Id("useOfEnglish", id);
    }

    public Object getUseTasksByLevelId(Long id) {
        return taskRepository.findTaskByLevelIdAndType(id, "useOfEnglish");

    }

    public List<Task> getWritingTasks(Long id) {
        return taskRepository.findAllByTypeAndLevel_Id("writing", id);
    }

    public Object getWritingTasksByLevelId(Long id) {
        return taskRepository.findTaskByLevelIdAndType(id, "writing");
    }
}
