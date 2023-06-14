package com.itlang.controllers.admin;

import com.itlang.models.EnglishLevelTestQuestion;
import com.itlang.repositories.EnglishLevelTestRepository;
import com.itlang.services.EnglishLevelTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminLevelTestController {

    private final EnglishLevelTestRepository englishLevelTestRepository;
    private final EnglishLevelTestService englishLevelTestService;

    @GetMapping("/level-test")
    public String getQuestions(Model model){
        model.addAttribute("question", new EnglishLevelTestQuestion());
        model.addAttribute("table", englishLevelTestRepository.findAllByOrderByQuestionLevel());
        return "admin/admin_level-test";
    }
    @PostMapping("/level-test/save-question")
    public String addQuestion(@ModelAttribute EnglishLevelTestQuestion question){
        englishLevelTestService.saveQuestion(question);
        return "redirect:/admin/level-test";
    }
    @GetMapping("/level-test/questions/{id}/edit")
    public String editQuestion(@PathVariable Long id, Model model){
        model.addAttribute("question", englishLevelTestRepository.findEnglishLevelTestQuestionById(id));
        return "english_level_test/edit-question";
    }
    @PostMapping("/level-test/questions/{id}/edit")
    public String saveEditedQuestion(@PathVariable Long id, @ModelAttribute EnglishLevelTestQuestion question){
        englishLevelTestService.saveEditedQuestion(id, question);
        return "redirect:/admin/level-test";
    }
    @GetMapping("/level-test/questions/{id}/delete")
    public String deleteQuestion(@PathVariable Long id){
        englishLevelTestService.deleteQuestion(id);
        return "redirect:/admin/level-test";
    }
}
