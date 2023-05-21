package com.itlang.controllers;

import com.itlang.models.EnglishLevelTestForm;
import com.itlang.models.EnglishLevelTestQuestion;
import com.itlang.repositories.EnglishLevelTestRepository;
import com.itlang.security.PersonDetails;
import com.itlang.services.EnglishLevelTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/english-level-test")
public class EnglishLevelTestController {

    private final EnglishLevelTestService englishLevelTestService;
    private final EnglishLevelTestRepository englishLevelTestRepository;

    @GetMapping("/questions")
    public String showQuestions(Model model){
        model.addAttribute("question", new EnglishLevelTestQuestion());
        model.addAttribute("table", englishLevelTestRepository.findAllByOrderByQuestionLevel());
        return "english_level_test/show-questions";
    }
    @PostMapping("/save-question")
    public String addQuestion(@ModelAttribute EnglishLevelTestQuestion question){
        englishLevelTestService.saveQuestion(question);
        return "redirect:/english-level-test/questions";
    }
    @GetMapping("/questions/{id}/edit")
    public String editQuestion(@PathVariable Long id, Model model){
        model.addAttribute("question", englishLevelTestRepository.findEnglishLevelTestQuestionById(id));
        return "english_level_test/edit-question";
    }
    @PostMapping("/questions/{id}/edit")
    public String saveEditedQuestion(@PathVariable Long id, @ModelAttribute EnglishLevelTestQuestion question){
        englishLevelTestService.saveEditedQuestion(id, question);
        return "redirect:/english-level-test/questions";
    }
    @GetMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable Long id){
        englishLevelTestService.deleteQuestion(id);
        return "redirect:/english-level-test/questions";
    }

    @GetMapping("/test")
    public String startTest(Model model){
        EnglishLevelTestForm qForm = englishLevelTestService.getQuestions();
        if(qForm.getQuestionList().size() < 25){
            model.addAttribute("isRight", false);
        } else if(qForm.getQuestionList().size() == 25){
            model.addAttribute("isRight", true);
            model.addAttribute("qForm", qForm);
        }

        return "english_level_test/test";
    }


    @PostMapping("/test/check")
    public String checkResults(@ModelAttribute EnglishLevelTestForm qForm, Model model){
        model.addAttribute("result", englishLevelTestService.checkResult(qForm));
        return "english_level_test/result";
    }

    @GetMapping("/intro")
    public String testIntro(){
        return "english_level_test/test-intro";
    }
}
