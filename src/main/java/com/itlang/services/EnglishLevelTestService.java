package com.itlang.services;

import com.itlang.models.EnglishLevelTestForm;
import com.itlang.models.EnglishLevelTestQuestion;
import com.itlang.models.Person;
import com.itlang.models.UserProgress;
import com.itlang.repositories.EnglishLevelTestRepository;
import com.itlang.repositories.PeopleRepository;
import com.itlang.repositories.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnglishLevelTestService {
    private final EnglishLevelTestRepository englishLevelTestRepository;
    private final EnglishLevelTestForm qForm;
    private final PeopleRepository peopleRepository;
    private final UserProgressRepository progressRepository;

    public void saveQuestion(EnglishLevelTestQuestion question){
        englishLevelTestRepository.save(question);
    }

    public void saveEditedQuestion(Long id, EnglishLevelTestQuestion question) {
        EnglishLevelTestQuestion questionFromDB = englishLevelTestRepository.findEnglishLevelTestQuestionById(id);
        questionFromDB.setQuestionTitle(question.getQuestionTitle());
        questionFromDB.setQuestionLevel(question.getQuestionLevel());
        questionFromDB.setQuestionAnswer1(question.getQuestionAnswer1());
        questionFromDB.setQuestionAnswer2(question.getQuestionAnswer2());
        questionFromDB.setQuestionAnswer3(question.getQuestionAnswer3());
        questionFromDB.setQuestionAnswer4(question.getQuestionAnswer4());
        questionFromDB.setQuestionCorrectAnswer(question.getQuestionCorrectAnswer());

        englishLevelTestRepository.save(questionFromDB);
    }

    public void deleteQuestion(Long id) {
        englishLevelTestRepository.deleteById(id);
    }

    public EnglishLevelTestForm getQuestions(){
        List<EnglishLevelTestQuestion> list = new ArrayList<>();

        List<EnglishLevelTestQuestion> temp = new ArrayList<>();
        temp = englishLevelTestRepository.findRandomQuestionsA1();
        for (int i = 0; i < temp.size(); i++) {
            list.add(temp.get(i));
        }

        temp = englishLevelTestRepository.findRandomQuestionsA2();
        for (int i = 0; i < temp.size(); i++) {
            list.add(temp.get(i));
        }
        temp = englishLevelTestRepository.findRandomQuestionsB1();
        for (int i = 0; i < temp.size(); i++) {
            list.add(temp.get(i));
        }
        temp = englishLevelTestRepository.findRandomQuestionsB2();
        for (int i = 0; i < temp.size(); i++) {
            list.add(temp.get(i));
        }
        temp = englishLevelTestRepository.findRandomQuestionsC();
        for (int i = 0; i < temp.size(); i++) {
            list.add(temp.get(i));
        }

        qForm.setQuestionList(list);
        return qForm;
    }

    public String checkResult(EnglishLevelTestForm qForm) {
        int result = 0;
        List<EnglishLevelTestQuestion> answers = new ArrayList<>();
        answers = qForm.getQuestionList();

        for (int i = 0; i < answers.size(); i++) {
            if(answers.get(i).getUser_answer() == answers.get(i).getQuestionCorrectAnswer()){
                result++;
            }
        }
        String res = null;
        if(result>=0 && result<=5) {
            res = "Ваш результат - " + result + "/25, що може свідчити про рівень володіння мовою - A1";
        }
        else if(result>=6 && result<=10){
            res = "Ваш результат - " + result + "/25, що може свідчити про рівень володіння мовою - A2";
        }
        else if(result>=11 && result<=15) {
            res = "Ваш результат - " + result + "/25, що може свідчити про рівень володіння мовою - B1";
        }
        else if(result>=16 && result<=20){
            res = "Ваш результат - " + result + "/25, що може свідчити про рівень володіння мовою - B2";
        }
        else if(result>=21 && result<=25){
            res = "Ваш результат - " + result + "/25, що може свідчити про рівень володіння мовою - C+";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!email.equals("anonymousUser")){

            Person person = peopleRepository.findPersonByEmail(email);
            UserProgress progress = person.getUserProgress();

            if(result>=0 && result<=5) progress.setEnglishLevel("A1");
            else if(result>=6 && result<=10) progress.setEnglishLevel("A2");
            else if(result>=11 && result<=15) progress.setEnglishLevel("B1");
            else if(result>=16 && result<=20) progress.setEnglishLevel("B2");
            else if(result>=21 && result<=25) progress.setEnglishLevel("C");

            progressRepository.save(progress);

        }
        return res;
    }
}
