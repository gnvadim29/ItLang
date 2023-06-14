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
