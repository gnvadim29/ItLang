package com.itlang.services.course;

import com.itlang.models.Image;
import com.itlang.models.Person;
import com.itlang.models.course.*;
import com.itlang.repositories.BlogPostImageRepository;
import com.itlang.repositories.PeopleRepository;
import com.itlang.repositories.course.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TaskRepository taskRepository;
    private final BlogPostImageRepository imageRepository;
    private final AnswerRepository answerRepository;
    private final CourseRepository courseRepository;
    private final PeopleRepository peopleRepository;
    private final UserQuestionsRepository userQuestionsRepository;

    public void addQuestion(Long id, QuestionBody questionBody, String type, MultipartFile image1, MultipartFile image2, MultipartFile image3) throws IOException {
        Task task = taskRepository.findTaskById(id);
        Question question = new Question();
        question.setTitle(questionBody.getTitle());

//        LISTENING
        if (type.equals("listening_text")){
            Answer answer1 = new Answer();
            Answer answer2 = new Answer();
            Answer answer3 = new Answer();

            answer1.setTitle(questionBody.getAnswer1());
            answer2.setTitle(questionBody.getAnswer2());
            answer3.setTitle(questionBody.getAnswer3());

            if(questionBody.getCorrectAnswer().equals("0")){
                answer1.setCorrect(true);
            }
            if(questionBody.getCorrectAnswer().equals("1")){
                answer2.setCorrect(true);
            }
            if(questionBody.getCorrectAnswer().equals("2")){
                answer3.setCorrect(true);
            }

            answerRepository.save(answer1);
            answerRepository.save(answer2);
            answerRepository.save(answer3);



            question.addAnswer(answer3);
            question.addAnswer(answer1);
            question.addAnswer(answer2);

            task.addQuestion(question);
        }
        if (type.equals("listening_true_false")){
            Answer answerTrue = new Answer();
            Answer answerFalse = new Answer();

            answerTrue.setTitle("true");
            answerFalse.setTitle("false");

            if (questionBody.getCorrectAnswer().equals("true")){
                answerTrue.setCorrect(true);
            } else {
                answerFalse.setCorrect(true);
            }
            answerRepository.save(answerFalse);
            answerRepository.save(answerTrue);

            question.addAnswer(answerTrue);
            question.addAnswer(answerFalse);

            task.addQuestion(question);
        }
        if (type.equals("listening_image_answer")){
// create Answer objects
            Answer answer1 = new Answer();
            Answer answer2 = new Answer();
            Answer answer3 = new Answer();

// set titles and correct answers
            if (questionBody.getCorrectAnswer().equals("0")) {
                answer1.setCorrect(true);
            }
            if (questionBody.getCorrectAnswer().equals("1")) {
                answer2.setCorrect(true);
            }
            if (questionBody.getCorrectAnswer().equals("2")) {
                answer3.setCorrect(true);
            }

// create Image objects and add them to Answer objects
            Image firstImage = toImageEntity(image1);
            imageRepository.save(firstImage);
            answer1.addImageToAnswer(firstImage);
            answerRepository.save(answer1);

            Image secondImage = toImageEntity(image2);
            imageRepository.save(secondImage);
            answer2.addImageToAnswer(secondImage);
            answerRepository.save(answer2);


            Image thirdImage = toImageEntity(image3);
            imageRepository.save(thirdImage);
            answer3.addImageToAnswer(thirdImage);
            answerRepository.save(answer3);

            // add Answer objects to Question
            question.addAnswer(answer1);
            question.addAnswer(answer2);
            question.addAnswer(answer3);

            task.addQuestion(question);
        }

//        READING
        if(type.equals("reading_text_answers")){
            Answer answer1 = new Answer();
            Answer answer2 = new Answer();
            Answer answer3 = new Answer();
            Answer answer4 = new Answer();


            answer1.setTitle(questionBody.getAnswer1());
            answer2.setTitle(questionBody.getAnswer2());
            answer3.setTitle(questionBody.getAnswer3());
            answer4.setTitle(questionBody.getAnswer4());

            if(questionBody.getCorrectAnswer().equals("0")){
                answer1.setCorrect(true);
            }
            if(questionBody.getCorrectAnswer().equals("1")){
                answer2.setCorrect(true);
            }
            if(questionBody.getCorrectAnswer().equals("2")){
                answer3.setCorrect(true);
            }
            if(questionBody.getCorrectAnswer().equals("3")){
                answer4.setCorrect(true);
            }

            answerRepository.save(answer1);
            answerRepository.save(answer2);
            answerRepository.save(answer3);
            answerRepository.save(answer4);

            question.addAnswer(answer1);
            question.addAnswer(answer2);
            question.addAnswer(answer3);
            question.addAnswer(answer4);

            task.addQuestion(question);
        }
        if(type.equals("reading_texts_answer")){
            Answer answer = new Answer();
            answer.setTitle(questionBody.getAnswer1());
            answer.setCorrect(true);
            answerRepository.save(answer);
            question.addAnswer(answer);
            question.setText(questionBody.getQuestionText());
            task.addQuestion(question);
        }

//        USE OF ENGLISH
        if(type.equals("use_text_answers")){
            Answer answer1 = new Answer();
            Answer answer2 = new Answer();
            Answer answer3 = new Answer();
            Answer answer4 = new Answer();

            answer1.setTitle(questionBody.getAnswer1());
            answer2.setTitle(questionBody.getAnswer2());
            answer3.setTitle(questionBody.getAnswer3());
            answer4.setTitle(questionBody.getAnswer4());

            if(questionBody.getCorrectAnswer().equals("0")){
                answer1.setCorrect(true);
            }
            if(questionBody.getCorrectAnswer().equals("1")){
                answer2.setCorrect(true);
            }
            if(questionBody.getCorrectAnswer().equals("2")){
                answer3.setCorrect(true);
            }
            if(questionBody.getCorrectAnswer().equals("3")){
                answer4.setCorrect(true);
            }

            answerRepository.save(answer1);
            answerRepository.save(answer2);
            answerRepository.save(answer3);
            answerRepository.save(answer4);

            question.addAnswer(answer1);
            question.addAnswer(answer2);
            question.addAnswer(answer3);
            question.addAnswer(answer4);

            task.addQuestion(question);
        }
        if(type.equals("use_text_paste")){
            Answer answer = new Answer();
            answer.setTitle(questionBody.getAnswer1());
            answer.setCorrect(true);
            answerRepository.save(answer);

            question.addAnswer(answer);
            task.addQuestion(question);
        }

//        WRITING
        if(type.equals("writing")){
            task.addQuestion(question);
        }


        taskRepository.save(task);

    }

    public List<Question> getQuestions(Long id) {
        return questionRepository.findQuestionsByTaskId(id);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findQuestionById(id);
        System.out.println(question.getId() + " +++ ");
        List<UserQuestions> userQuestions = userQuestionsRepository.findUserQuestionsByQuestionId(question.getId());
        for (int i = 0; i < userQuestions.size(); i++){
            System.out.println(userQuestions.get(i).getId());
            userQuestionsRepository.delete(userQuestions.get(i));
        }

        List<Answer> answers = questionRepository.findQuestionById(id).getAnswers();
        if (answers.size() != 0){
            if(answers.get(0).getImage()!=null){
                for (Answer answer : answers) {
                    imageRepository.deleteImageById(answer.getImage().getId());
                    answerRepository.deleteById(answer.getId());
                }
            }
        }

        questionRepository.deleteById(id);

    }
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());

        return image;
    }

    public void checkQuestions(List<CheckQuestion> answers, String type) {
        //find userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());

        for (int i = 0; i<answers.size(); i++){
            Question question = questionRepository.findQuestionById(answers.get(i).getId());
            UserQuestions userQuestions = userQuestionsRepository.findUserQuestionsByQuestionIdAndPersonId(question.getId(), person.getId());

                if (userQuestions != null){
                    if (question.getCorrectAnswerId().equals(answers.get(i).getUserAnswer())){
                        userQuestions.setCorrect(true);
                    } else {
                        userQuestions.setCorrect(false);
                    }
                    userQuestions.setLastAnswer(answers.get(i).getUserAnswer());
                    userQuestionsRepository.save(userQuestions);
                }else {
                    UserQuestions userQuestions1 = new UserQuestions();
                    userQuestions1.setQuestionId(question.getId());
                    userQuestions1.setPersonId(person.getId());
                    userQuestions1.setLevelId(question.getTask().getLevel().getId());
                    userQuestions1.setCourseTitle(question.getTask().getLevel().getCourse().getTitle());

                    if (question.getCorrectAnswerId().equals(answers.get(i).getUserAnswer())){
                        userQuestions1.setCorrect(true);
                    } else {
                        userQuestions1.setCorrect(false);
                    }
                    userQuestions1.setLastAnswer(answers.get(i).getUserAnswer());
                    userQuestionsRepository.save(userQuestions1);
                }
        }




    }



}