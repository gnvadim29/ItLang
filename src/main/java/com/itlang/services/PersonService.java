package com.itlang.services;

import com.itlang.models.Image;
import com.itlang.models.Person;
import com.itlang.models.UserProgress;
import com.itlang.models.course.UserQuestions;
import com.itlang.repositories.BlogPostImageRepository;
import com.itlang.repositories.PeopleRepository;
import com.itlang.repositories.UserProgressRepository;
import com.itlang.repositories.course.UserQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PeopleRepository peopleRepository;
    private final UserQuestionsRepository userQuestionsRepository;
    private final UserProgressRepository userProgressRepository;
    private final BlogPostImageRepository imageRepository;

    public void deleteUser(Long id){
        Person person = peopleRepository.findPersonById(id);

        UserProgress userProgress = userProgressRepository.findUserProgressById(person.getUserProgress().getId());
        userProgressRepository.delete(userProgress);

        List<UserQuestions> userQuestions = userQuestionsRepository.findUserQuestionsByPersonId(id);
        for (int i = 0; i < userQuestions.size(); i++){
            userQuestionsRepository.delete(userQuestions.get(i));
        }

        if(person.getUserIconId()!=null){
            Image image = imageRepository.findImageById(person.getUserIconId());
            imageRepository.delete(image);
        }

        peopleRepository.delete(person);
    }

}
